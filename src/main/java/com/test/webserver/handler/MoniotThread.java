package com.test.webserver.handler;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * Class to monitor threads
 * 
 * @author SRINI
 *
 */
public class MoniotThread implements Runnable {

	private static Logger logger = Logger.getLogger(MoniotThread.class.getName());

	private ThreadPoolExecutor threadPoolExecutor;

	public MoniotThread(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	public void run() {
		try {
			do {
				logger.info("Monitor Thread stats --> " + String.format(
						"[%d/%d] Active Threads: %d, Completed Threads: %d, Task count: %d, queue Size: %d",
						threadPoolExecutor.getPoolSize(), threadPoolExecutor.getCorePoolSize(),
						threadPoolExecutor.getActiveCount(), threadPoolExecutor.getCompletedTaskCount(),
						threadPoolExecutor.getTaskCount(), threadPoolExecutor.getQueue().size()));
				Thread.sleep(10000);
			} while (true);
		} catch (InterruptedException e) {
			logger.severe("Exception in Monitor Thread - " + e.getMessage());
		}
	}

}
