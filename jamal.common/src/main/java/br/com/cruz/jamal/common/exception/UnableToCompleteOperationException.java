package br.com.cruz.jamal.common.exception;

public class UnableToCompleteOperationException extends JamalException {

	private static final long serialVersionUID = -7953106171742461875L;

	public UnableToCompleteOperationException(String operationName, String cause) {
		// TODO: FAZER A LÓGICA
	}
	
	public UnableToCompleteOperationException(String operationName, Throwable cause) {
		super(operationName, cause);
		// TODO: FAZER A LÓGICA
	}
}
