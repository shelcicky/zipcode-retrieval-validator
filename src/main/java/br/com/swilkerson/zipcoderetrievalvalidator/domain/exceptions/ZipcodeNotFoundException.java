package br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions;

public class ZipcodeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "The zipcode %s was not found.";

    public ZipcodeNotFoundException(String zipcode) {
        super(String.format(MESSAGE, zipcode));
    }
}
