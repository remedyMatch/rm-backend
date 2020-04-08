package io.remedymatch.domain;

/**
 * Die Operation ist nicht erlaubt
 */
public class OperationNotAlloudException extends RuntimeException {
	private static final long serialVersionUID = -1154449640956861571L;

	public OperationNotAlloudException(String message) {
		super(message);
	}
}
