package com.example.demo.error;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CustomErrorResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private int status;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String error;

	public CustomErrorResponse(LocalDateTime timestamp, int status, String error) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;

	}
}
