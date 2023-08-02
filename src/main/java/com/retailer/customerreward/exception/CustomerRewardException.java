package com.retailer.customerreward.exception;

public class CustomerRewardException extends Exception{

	private static final long serialVersionUID = 5485661983669624825L;

	public CustomerRewardException() {
		super();
	}


	public CustomerRewardException(String message) {
		super(message);
	}

	public CustomerRewardException(Throwable cause) {
		super(cause);
	}
	

}
