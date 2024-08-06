package com.ecommerce.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class ErrorResponse {
	private Date timestamp;
	private String message;
	private String details;

	public ErrorResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}


}
