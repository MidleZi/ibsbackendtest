package ru.zaychikov.ibsbackendtest.exceptions.document;

public class DocumentNotFoundException extends RuntimeException{

    public DocumentNotFoundException(String message) {
        super(message);
    }
}
