package io.remedymatch.domain;

/**
 * Die Operation ist nicht erlaubt
 */
public class OperationNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = -1154449640956861571L;

    public OperationNotAllowedException(String message) {
        super(message);
    }
}
