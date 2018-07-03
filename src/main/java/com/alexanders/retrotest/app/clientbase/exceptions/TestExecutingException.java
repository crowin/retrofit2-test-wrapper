package com.alexanders.retrotest.app.clientbase.exceptions;

/**
 * @author astolnikov: 09.04.2018
 */

/**
 * Custom exception class which can to use without try-catch block if this need your
 */
public class TestExecutingException extends RuntimeException {
    private String message;

    public TestExecutingException(String message) {
        this.message = "Some problem occurred during test execution: " + message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
