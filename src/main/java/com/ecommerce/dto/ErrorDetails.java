package com.ecommerce.dto;

import java.util.Date;

public class ErrorDetails {
	        private Date timestamp;
	        private String errorMessage;
	        private String details;

	        public ErrorDetails(Date timestamp, String errorMessage, String details) {
	            super();
	            this.timestamp = timestamp;
	            this.errorMessage = errorMessage;
	            this.details = details;
	        }

			public Date getTimestamp() {
				return timestamp;
			}

			public void setTimestamp(Date timestamp) {
				this.timestamp = timestamp;
			}

			

			public String getErrorMessage() {
				return errorMessage;
			}

			public void setErrorMessage(String errorMessage) {
				this.errorMessage = errorMessage;
			}

			public String getDetails() {
				return details;
			}

			public void setDetails(String details) {
				this.details = details;
			}

	        
	    }

