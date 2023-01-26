package org.itclinicaltest.error;

public class NoProcessTypeFoundException extends RuntimeException {

    public NoProcessTypeFoundException(String message) {
        super("No process type found for the input: " + message);
    }
}
