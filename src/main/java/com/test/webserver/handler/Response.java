package com.test.webserver.handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.test.webserver.types.ContentType;
import com.test.webserver.types.HttpMethod;
import com.test.webserver.types.HttpStatusCode;

/**
 * Class to build http response.
 * 
 * @author SRINI
 *
 */
public class Response {
	private static Logger log = Logger.getLogger(Response.class.getName());

	public static final String VERSION = "HTTP/1.1";
	private Map<String, String> headers = new HashMap<>();
	private byte[] responseBody;

	public Response(Request request) throws IOException {

		HttpMethod httpMethod = request.getHttpMethod();

		switch (httpMethod) {
		case HEAD:
			addDefaultHeaders(HttpStatusCode.STATUS200);
			break;
		case GET:
			try {
				processGetResponse(request);
			} catch (Exception e) {
				log.error("Response Error ", e);
				addDefaultHeaders(HttpStatusCode.STATUS400);
				responseBody = HttpStatusCode.STATUS400.toString().getBytes();
			}

			break;
		case UNKNOWN:
			addDefaultHeaders(HttpStatusCode.STATUS400);
			responseBody = HttpStatusCode.STATUS400.toString().getBytes();
			break;
		default:
			addDefaultHeaders(HttpStatusCode.STATUS501);
			responseBody = HttpStatusCode.STATUS501.toString().getBytes();
		}
	}

	public void write(OutputStream outputStream) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
		for (String key : headers.keySet()) {
			writer.write(key + ":" + headers.get(key));
			writer.write("\r\n");
		}

		if (responseBody != null) {
			InputStream is = new ByteArrayInputStream(responseBody);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			char[] buffer = new char[1024];
			int read;
			while ((read = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, read);
			}
			is.close();
			reader.close();
		}

		writer.flush();
	}

	private void processGetResponse(Request request) throws IOException {
		
		File file = new File("." + request.getUri());
		if (file.isDirectory()) {
			headers.put("Content-Type", ContentType.HTML.toString());
			StringBuilder result = new StringBuilder("<html><head><title>Index of - ");
			result.append(request.getUri());
			result.append("</title></head><body><h1>Index of  - ");
			result.append(request.getUri());
			result.append("</h1><hr><pre>");

			File[] files = file.listFiles();
			for (File subDirfile : files) {
				result.append(" <a href=\"" + subDirfile.getPath() + "\">" + subDirfile.getPath() + "</a>\n");
			}
			result.append("<hr></pre></body></html>");
			addDefaultHeaders(HttpStatusCode.STATUS200);
			responseBody = result.toString().getBytes();
		} else if (file.exists()) {
			try {
				String ext = request.getUri().substring(request.getUri().indexOf(".") + 1);
				headers.put("Content-type", ContentType.valueOf(ext.toUpperCase()).toString());
				addDefaultHeaders(HttpStatusCode.STATUS200);
				responseBody = getBytesFromFile(file);
			} catch (Exception e) {
				log.error("Content type not found in the request: ", e);
			}

		} else {
			log.info("File not found:" + request.getUri());
			addDefaultHeaders(HttpStatusCode.STATUS404);
			responseBody = HttpStatusCode.STATUS404.toString().getBytes();
		}
	}

	private byte[] getBytesFromFile(File file) throws IOException {
		int fileLength = (int) file.length();
		byte[] byteArray = new byte[fileLength];
		InputStream in = new FileInputStream(file);
		int offset = 0;
		while (offset < fileLength) {
			int count = in.read(byteArray, offset, (fileLength - offset));
			offset += count;
		}
		in.close();
		return byteArray;
	}

	private void addDefaultHeaders(HttpStatusCode httpStatusCode) {
		headers.put("Server", PooledWebServer.SERVER_NAME);
		headers.put(Response.VERSION, httpStatusCode.toString());
		headers.put("Date", Instant.now().toString());
		headers.put("Connection", "keep-alive");
	}

}
