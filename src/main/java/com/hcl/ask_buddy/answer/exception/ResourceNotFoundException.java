package com.hcl.ask_buddy.answer.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String msg;

	public ResourceNotFoundException(String msg) {
		super(msg);
		this.msg = msg;
	}

}
