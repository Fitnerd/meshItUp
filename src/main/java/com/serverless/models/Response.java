package com.serverless.models;

public class Response {

	private final String message;
	private final Input input;

	public Response(String message, Input input2) {
		this.message = message;
		this.input = input2;
	}

	public String getMessage() {
		return this.message;
	}

	public Input getInput() {
		return this.input;
	}
}
