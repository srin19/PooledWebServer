package com.test.webserver.types;

public enum ContentType {

	HTML("HTML"), //
	HTM("HTM"), //

	CSS("CSS"), //

	ICO("ICO"), //
	JPG("JPG"), //
	JPEG("JPEG"), //
	PNG("PNG"), //

	TXT("TXT"), //
	XML("XML"); //

	private final String fileExtension;

	ContentType(String ext) {
		this.fileExtension = ext;
	}

	public String getFileExtension() {
		return fileExtension;
	}
}
