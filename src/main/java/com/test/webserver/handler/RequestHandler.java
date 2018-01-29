package com.test.webserver.handler;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Request handler class to read requests and create response objects.
 * 
 * @author SRINI
 *
 */
public class RequestHandler implements Runnable {

	private static Logger logger = Logger.getLogger(RequestHandler.class.getName());

	private Socket socket;

	public RequestHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			Request request = new Request(socket.getInputStream());
			Response response = new Response(request);
			response.write(socket.getOutputStream());
			socket.close();
		} catch (Exception exception) {
			logger.log(Level.SEVERE, "Runtime exception...", exception);
		}
	}

}
