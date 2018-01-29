package com.test.webserver.types;

/**
 * Enum class to represent http status codes
 * 
 * @author SRINI
 *
 */
public enum HttpStatusCode {

	STATUS200("200 - Ok"), //
	STATUS201("201 - Created"), //
	STATUS202("202 - Accepted"), //
	STATUS203("203 - Non authorative Info"), //
	STATUS204("204 - No content"), //
	STATUS205("205 - Reset content"), //
	STATUS206("206 - Partial content"), //

	/** 3XX: Relocation / Redirect */
	STATUS300("300 - Multiple choices"), //
	STATUS301("301 - Moved permanently"), //
	STATUS302("302 - Moved temporarily"), //
	STATUS303("303 - Found"), //
	STATUS304("304 - Status not modified"), //
	STATUS305("305 - Use proxy"), //
	STATUS307("Temporary redirect"), //

	/** 4XX: Client error */
	STATUS400("400 - Bad request"), //
	STATUS401("401 - Unauthorized"), //
	STATUS402("402 - Payment Required"), //
	STATUS403("403 - Forbidden"), //
	STATUS404("404 - Not found"), //
	STATUS405("405 - Bad method"), //
	STATUS406("406 - Not acceptable"), //
	STATUS407("407 - Proxy auth"), //
	STATUS408("408 - Client timeout"), //
	STATUS409("409 - Conflict"), //
	STATUS410("410 - Gone"), //
	STATUS411("411 - Length required"), //
	STATUS412("412 - Precondition failed"), //
	STATUS413("413 - Entity too large"), //
	STATUS414("414 - URI too long"), //
	STATUS415("415 - Unsupported media type"), //

	/** 5XX: Server error */
	STATUS500("500 - Server error"), //
	STATUS501("501 - Internal server error"), //
	STATUS502("502 - Bad gateway"), //
	STATUS503("503 - Unavailable"), //
	STATUS504("504 - Gateway Timeout"), //
	STATUS505("505 - Version not supported");

	private final String httpCode;

	HttpStatusCode(String code) {
		this.httpCode = code;
	}

	public String getHttpCode() {
		return httpCode;
	}
}
