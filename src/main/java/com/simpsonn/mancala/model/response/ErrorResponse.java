package com.simpsonn.mancala.model.response;

/**
 * a container which defines the body of any error messages we may want to send
 * back to the consumer of the API
 */
public class ErrorResponse {
		
	/**
	 * create error message response body
	 * @param httpCode the http status code number
	 * @param message a descriptive message to send back
	 */
	public ErrorResponse(long httpCode, String message) {
		
		this.httpCode = httpCode;
		this.message = message;
	}
	
	private final long httpCode;
	private final String message;
	
	/**
	 * get the error code as a number
	 * @return the http error code number
	 */
	public long getHttpCode() {
		
		return httpCode;
	}

	/**
	 * get the error message description
	 * @return the error message
	 */
	public String getMessage() {

		return message;
	}

}
