package com.test.webserver.types;

/**
 * Enum class to represent http methods.
 * 
 * @author SRINIVAS
 *
 */
public enum HttpMethod {

	/**
	 * Safe http methods
	 */
	OPTIONS("OPTIONS"), //
	GET("GET"), //
	HEAD("HEAD"), //
	TRACE("TRACE"), //

	/**
	 * Unsafe http methods
	 */
	POST("POST"), //
	PUT("PUT"), //
	DELETE("DELETE"), //
	CONNECT("CONNECT"), //
	UNKNOWN(null); //

	private final String requestType;

	HttpMethod(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestType() {
		return requestType;
	}
}
