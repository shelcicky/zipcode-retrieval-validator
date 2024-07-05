package br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions;

public class InvalidZipcodeException extends RuntimeException {
    private static final String MESSAGE = "%s is not a valid zipcode!";

    public InvalidZipcodeException(String zipcode) {
        super(String.format(MESSAGE, zipcode));
    }
}
