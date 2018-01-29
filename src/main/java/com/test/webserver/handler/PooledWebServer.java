package com.test.webserver.handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application class that starts web server
 * 
 * @author SRINI
 *
 */
public class PooledWebServer extends Thread {

	private static Logger logger = Logger.getLogger(PooledWebServer.class.getName());
	private static final int WORKER_THREAD_COUNT = 5;
	private static int PORT = 8080;
	private ThreadPoolExecutor threadPoolExecutor;
	private ServerSocket serverSocket;
	private LinkedBlockingQueue<Runnable> threadConnections;
	private Thread monitThread;

	public static final String SERVER_NAME = "Pooled Web Server";

	public static void main(String[] args) {
		try {
			new PooledWebServer().startServer();
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Could not start server", ex);
		}
	}

	private void startServer() throws IOException {

		serverSocket = new ServerSocket(PORT);
		threadConnections = new LinkedBlockingQueue<>();
		threadPoolExecutor = new ThreadPoolExecutor(WORKER_THREAD_COUNT, WORKER_THREAD_COUNT, 10, TimeUnit.SECONDS,
				threadConnections);
		// create monitor thread
		monitThread = new Thread(new MoniotThread(threadPoolExecutor));
		monitThread.setDaemon(true);
		monitThread.start();

		System.out.println("Server started listening on port number: " + PORT);

		while (true) {
			threadPoolExecutor.submit(new RequestHandler(serverSocket.accept()));
		}
	}

}
