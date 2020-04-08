package io.remedymatch.domain;

/**
 * Der angefragte Objekt wurde nicht gefunden
 */
public class ObjectNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7752867930950525714L;

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
