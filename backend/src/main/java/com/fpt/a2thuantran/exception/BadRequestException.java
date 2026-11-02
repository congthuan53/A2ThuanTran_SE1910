package com.fpt.a2thuantran.exception;

/**
 * Thrown when the request breaks a business rule, for example:
 * deleting an account that already created news articles.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
