package com.ejbank.pojos;

public class ResponseValidationPOJO {
	private boolean result;
	private String message;
	private String error;
	
	public ResponseValidationPOJO(boolean result, String message, String error) {
		this.result = result;
		this.message = message;
		this.error = error;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ResponseValidationPOJO [result=" + result + ", message=" + message + ", error=" + error + "]";
	}
	
	
	
	
}
