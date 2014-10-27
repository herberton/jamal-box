package br.com.cruz.jamal.common.exception;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class JamalException extends Exception {

	private static final long serialVersionUID = 3795459060108965263L;

	
	public JamalException(String message) {
        super(message);
    }
	
	public JamalException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public JamalException(Throwable cause) {
        super(cause);
    }
	
	protected JamalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}