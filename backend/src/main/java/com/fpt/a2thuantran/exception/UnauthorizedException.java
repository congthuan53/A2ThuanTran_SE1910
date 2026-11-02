package com.fpt.a2thuantran.exception;

/**
 * Thrown when login fails (wrong email/password) or the login token
 * is missing / invalid / expired.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}
