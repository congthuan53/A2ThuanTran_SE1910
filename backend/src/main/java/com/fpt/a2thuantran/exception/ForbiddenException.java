package com.fpt.a2thuantran.exception;

/**
 * Thrown when the logged-in account does not have the correct role
 * to call this API, for example: a Staff calling an Admin-only API.
 */
public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
