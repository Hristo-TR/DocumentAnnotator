package org.example.documentannotator.data.exception;

public class DuplicateDocumentException extends RuntimeException {
    public DuplicateDocumentException(String message) {
        super(message);
    }

    public DuplicateDocumentException() {
        super();
    }
}
