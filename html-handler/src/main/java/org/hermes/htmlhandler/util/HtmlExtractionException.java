package org.hermes.htmlhandler.util;

import org.hermes.core.utils.exception.DefaultExtractorException;

public class HtmlExtractionException extends DefaultExtractorException {

    public HtmlExtractionException() { }

    public HtmlExtractionException(String message) {
        super(message);
    }

    public HtmlExtractionException(String message, Throwable cause) {
        super(message, cause);
    }

    public HtmlExtractionException(Throwable cause) {
        super(cause);
    }
}
