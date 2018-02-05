package com.test.webserver.handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * Application class that starts web server
 * 
 * @author SRINI
 *
 */
public class PooledWebServer extends Thread {

	private static Logger logger = Logger.getLogger(PooledWebServer.class);
	private static final int WORKER_THREAD_COUNT = 5;
	private static int DEFAULT_HTTP_PORT = 8080;
	private ThreadPoolExecutor threadPoolExecutor;
	private ServerSocket serverSocket;
	private LinkedBlockingQueue<Runnable> threadConnections;
	private Thread monitoringThread;

	public static final String SERVER_NAME = "Pooled Web Server";

	public static void main(String[] args) {
		try {
			new PooledWebServer().startServer(extractPortParam(args));
		} catch (Exception ex) {
			logger.error("Could not start server", ex);
		}
	}

	private void startServer(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			threadConnections = new LinkedBlockingQueue<>();
			threadPoolExecutor = new ThreadPoolExecutor(WORKER_THREAD_COUNT, WORKER_THREAD_COUNT, 10, TimeUnit.SECONDS,
					threadConnections);
			// create monitor thread
			monitoringThread = new Thread(new MoniotorThread(threadPoolExecutor));
			monitoringThread.setDaemon(true);
			monitoringThread.start();

			System.out.println("Server started listening on port number: " + portNumber);

			while (true) {
				threadPoolExecutor.execute(new RequestHandler(serverSocket.accept()));
			}

		} catch (IOException e) {
			logger.error("Server encountered error.", e);
		}

	}

	/**
	 * Parse commandline args to see if there's a valid port number
	 * 
	 * @return int
	 */
	private static int extractPortParam(String args[]) throws NumberFormatException {
		if (args.length > 0) {
			int portNumber = Integer.parseInt(args[0]);
			if (portNumber > 0 && portNumber < 65535) {
				return portNumber;
			} else {
				throw new NumberFormatException(" Invalid portnumer passed. Port number should be between 0 and 65535");
			}
		}
		return DEFAULT_HTTP_PORT;
	}

}
