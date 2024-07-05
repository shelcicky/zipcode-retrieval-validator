package br.com.swilkerson.zipcoderetrievalvalidator.config.exceptions.model;

import java.time.LocalDateTime;

public record DefaultException(LocalDateTime timestamp, int statusCode, String error, String detail) {
}
