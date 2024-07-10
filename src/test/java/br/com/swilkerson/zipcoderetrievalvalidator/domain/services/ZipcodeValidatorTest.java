package br.com.swilkerson.zipcoderetrievalvalidator.domain.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ZipcodeValidator.class
})
class ZipcodeValidatorTest {
    private static final String INVALID_ZIPCODE_SHORT = "1234567";
    private static final String INVALID_ZIPCODE_LARGE = "123456789";
    private static final String INVALID_ZIPCODE_INVALID_CHAR = "01001*000";
    private static final String NULL_ZIPCODE = null;
    private static final String NOT_FOUND_ZIPCODE_WITH_DASH = "00000-000";
    private static final String NOT_FOUND_ZIPCODE_WITHOUT_DASH = "00000000";
    private static final String VALID_ZIPCODE_WITH_DASH = "01001-000";
    private static final String VALID_ZIPCODE_WITHOUT_DASH = "01001000";

    @Autowired
    private ZipcodeValidator isValidZipcode;

    @Test
    void shouldReturnFalseWithShortZipcode() {
        assertFalse(isValidZipcode.execute(INVALID_ZIPCODE_SHORT));
    }

    @Test
    void shouldReturnFalseWithLargeZipcode() {
        assertFalse(isValidZipcode.execute(INVALID_ZIPCODE_LARGE));
    }

    @Test
    void shouldReturnFalseWithInvalidCharZipcode() {
        assertFalse(isValidZipcode.execute(INVALID_ZIPCODE_INVALID_CHAR));
    }

    @Test
    void shouldReturnFalseWithNullZipcode() {
        assertFalse(isValidZipcode.execute(NULL_ZIPCODE));
    }

    @Test
    void shouldReturnTrueWithNotFoundZipcode() {
        assertTrue(isValidZipcode.execute(NOT_FOUND_ZIPCODE_WITH_DASH));
    }

    @Test
    void shouldReturnTrueWithNotFoundZipcodeNoDash() {
        assertTrue(isValidZipcode.execute(NOT_FOUND_ZIPCODE_WITHOUT_DASH));
    }

    @Test
    void shouldReturnTrueWithValidZipcode() {
        assertTrue(isValidZipcode.execute(VALID_ZIPCODE_WITH_DASH));
    }

    @Test
    void shouldReturnTrueWithValidZipcodeNoDash() {
        assertTrue(isValidZipcode.execute(VALID_ZIPCODE_WITHOUT_DASH));
    }
}