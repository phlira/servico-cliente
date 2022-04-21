package com.suritec.servicocliente.exception;

public class ErroAutenticacaoException extends RuntimeException{

	private static final long serialVersionUID = -3712036533886369640L;
	
	public ErroAutenticacaoException(String msg) {
		super(msg);
	}
	
}
