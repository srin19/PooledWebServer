package com.test.webserver.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.test.webserver.types.HttpMethod;

public class RequestTest {

	@Test
	public void testHeadRequest() throws IOException {
		Request req = new Request(new ByteArrayInputStream("HEAD / HTTP/1.1\n\n".getBytes()));
		Assert.assertEquals(HttpMethod.HEAD, req.getHttpMethod());
	}

	@Test
	public void testGetRequest() throws IOException {
		Request req = new Request(new ByteArrayInputStream("GET / HTTP/1.1\n\n".getBytes()));
		Assert.assertEquals(HttpMethod.GET, req.getHttpMethod());
	}

	@Test
	public void testUnknownRequest() throws IOException {
		Request req = new Request(new ByteArrayInputStream("QWERTY / HTTP/1.1\n\n".getBytes()));
		Assert.assertEquals(HttpMethod.UNKNOWN, req.getHttpMethod());
	}
}
