package com.fpt.a2thuantran.exception;

/**
 * Thrown when we look up something (account, category, news article, ...) by id
 * but it does not exist in the database.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
