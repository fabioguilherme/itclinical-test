package org.itclinicaltest.error;

public class ValidatorNotFoundException extends RuntimeException {

    public ValidatorNotFoundException(String message) {
        super(message);
    }
}
