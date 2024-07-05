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
    private static final String INVALID_ZIPCODE_SHORT = "0505000";
    private static final String INVALID_ZIPCODE_LARGE = "123456789";
    private static final String INVALID_ZIPCODE_INVALID_CHAR = "01001*000";
    private static final String NULL_ZIPCODE = null;
    private static final String NOT_FOUND_ZIPCODE_WITH_DASH = "00000-000";
    private static final String NOT_FOUND_ZIPCODE_WITHOUT_DASH = "00000000";
    private static final String VALID_ZIPCODE_WITH_DASH = "01001-000";
    private static final String VALID_ZIPCODE_WITHOUT_DASH = "01001000";

    @Autowired
    private ZipcodeValidator validator;

    @Test
    void shouldReturnFalseWithShortZipcode() {
        assertFalse(validator.execute(INVALID_ZIPCODE_SHORT));
    }

    @Test
    void shouldReturnFalseWithLargeZipcode() {
        assertFalse(validator.execute(INVALID_ZIPCODE_LARGE));
    }

    @Test
    void shouldReturnFalseWithInvalidCharZipcode() {
        assertFalse(validator.execute(INVALID_ZIPCODE_INVALID_CHAR));
    }

    @Test
    void shouldReturnFalseWithNullZipcode() {
        assertFalse(validator.execute(NULL_ZIPCODE));
    }

    @Test
    void shouldReturnTrueWithNotFoundZipcode() {
        assertTrue(validator.execute(NOT_FOUND_ZIPCODE_WITH_DASH));
    }

    @Test
    void shouldReturnTrueWithNotFoundZipcodeNoDash() {
        assertTrue(validator.execute(NOT_FOUND_ZIPCODE_WITHOUT_DASH));
    }

    @Test
    void shouldReturnTrueWithValidZipcode() {
        assertTrue(validator.execute(VALID_ZIPCODE_WITH_DASH));
    }

    @Test
    void shouldReturnTrueWithValidZipcodeNoDash() {
        assertTrue(validator.execute(VALID_ZIPCODE_WITHOUT_DASH));
    }
}