package com.test.webserver.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.test.webserver.types.HttpMethod;

/**
 * Request class that parses the request data and assign them to members.
 * 
 * @author SRINI
 *
 */
public class Request {

	private static Logger logger = Logger.getLogger(Request.class.getName());
	private HttpMethod httpMethod;
	private String uri;
	private String httpVersion;
	private HashMap<String, String> headers;
	private HashMap<String, String> params;
	private String path;
	private String query;

	public Request(InputStream inputStream) throws IOException {

		this.headers = new HashMap<>();
		this.params = new HashMap<>();

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String requestLine = reader.readLine();

		if (requestLine == null) {
			// TO DO - throw error
			return;
		}

		// extract request method
		String[] requestLineArr = extractRequestMethod(requestLine);

		uri = requestLineArr[1];
		httpVersion = requestLineArr[2];

		String line = reader.readLine();
		while (!line.equals("")) {
			String[] lineParts = line.split(":", 2);
			if (lineParts.length == 2) {
				headers.put(lineParts[0], lineParts[1]);
			}
			line = reader.readLine();
		}

		String[] uriArr = uri.split("\\?", 2);
		if (uriArr.length == 2) {
			path = uriArr[0];
			query = uriArr[1];

			String[] paramPairs = query.split("&");
			for (String kv : paramPairs) {
				String[] keyValue = kv.split("=", 2);
				if (keyValue.length == 2) {
					logger.info("Header: " + keyValue[0] + " value: " + keyValue[1]);
					params.put(keyValue[0], keyValue[1]);
				}
			}
		} else {
			path = uri;
			query = "";
		}
	}

	/**
	 * Method to extract http method. Sets UNKNOWN if none.
	 * 
	 * @param requestLine
	 * @return
	 */
	private String[] extractRequestMethod(String requestLine) {
		String[] requestLineArr = requestLine.split("\\s+");
		try {
			httpMethod = HttpMethod.valueOf(requestLineArr[0]);
		} catch (Exception e) {
			httpMethod = HttpMethod.UNKNOWN;
		}
		return requestLineArr;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public String getUri() {
		return uri;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	public String getPath() {
		return path;
	}

	public String getQuery() {
		return query;
	}

}
