package org.hermes.core.utils.exception;

public class ExtractorException extends RuntimeException {

    public ExtractorException() { }

    public ExtractorException(String message) {
        super(message);
    }

    public ExtractorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtractorException(Throwable cause) {
        super(cause);
    }
}
