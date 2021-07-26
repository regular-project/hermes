package org.hermes.core.utils.exception;

public class DefaultExtractorException extends Exception {

    public DefaultExtractorException() {
    }

    public DefaultExtractorException(String message) {
        super(message);
    }

    public DefaultExtractorException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefaultExtractorException(Throwable cause) {
        super(cause);
    }
}
