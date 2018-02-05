package com.test.webserver.handler;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	Logger logger;

	public Log(String name) {
		logger = Logger.getLogger(name);
		logger.setLevel(Level.INFO);
		logger.addHandler(new ConsoleHandler());
	}

	public static Log getLogger(@SuppressWarnings("rawtypes") Class className) {
		return new Log(className.getName());
	}

	public void error(String msg, Throwable thrown) {
		logger.log(Level.SEVERE, msg, thrown);
	}

	public void info(String msg) {
		logger.info(msg);
	}

}
