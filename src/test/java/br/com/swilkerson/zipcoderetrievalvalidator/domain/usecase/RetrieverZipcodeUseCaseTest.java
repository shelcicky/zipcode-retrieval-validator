package br.com.swilkerson.zipcoderetrievalvalidator.domain.usecase;

import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.RetrieverZipcodeDataProvider;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions.InvalidZipcodeException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions.ZipcodeNotFoundException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.services.ZipcodeValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        RetrieverZipcodeUseCase.class
})
class RetrieverZipcodeUseCaseTest {
    private static final byte ONCE = 1;
    private static final boolean VALID_ZIPCODE = true;
    private static final String INVALID_ZIPCODE_SHORT = "1234567";
    private static final String INVALID_ZIPCODE_LARGE = "123456789";
    private static final String INVALID_ZIPCODE_INVALID_CHAR = "01001*000";
    private static final String NULL_ZIPCODE = null;
    private static final String NOT_FOUND_ZIPCODE_WITH_DASH = "00000-000";
    private static final String NOT_FOUND_ZIPCODE_WITHOUT_DASH = "00000000";
    private static final String VALID_ZIPCODE_WITH_DASH = "01001-000";
    private static final String VALID_ZIPCODE_WITHOUT_DASH = "01001000";
    private static final Optional<Zipcode> FOUND_ZIPCODE = Optional.of(new Zipcode(VALID_ZIPCODE_WITH_DASH,
            "Praça da Sé", "lado ímpar", "", "Sé", "São Paulo", "SP"));

    @MockBean
    private ZipcodeValidator isValidZipcode;

    @MockBean
    private RetrieverZipcodeDataProvider dataProvider;

    @Autowired
    private RetrieverZipcodeUseCase useCase;

    @Test
    void shouldThrowInvalidZipcodeWithShortZipcode() {
        when(isValidZipcode.execute(INVALID_ZIPCODE_SHORT)).thenThrow(InvalidZipcodeException.class);

        assertThatThrownBy(() -> useCase.execute(INVALID_ZIPCODE_SHORT)).isInstanceOf(InvalidZipcodeException.class);

        verifyNoInteractions(dataProvider);
        verify(isValidZipcode, times(ONCE)).execute(INVALID_ZIPCODE_SHORT);
    }

    @Test
    void shouldThrowInvalidZipcodeWithLargeZipcode() {
        when(isValidZipcode.execute(INVALID_ZIPCODE_LARGE)).thenThrow(InvalidZipcodeException.class);

        assertThatThrownBy(() -> useCase.execute(INVALID_ZIPCODE_LARGE)).isInstanceOf(InvalidZipcodeException.class);

        verifyNoInteractions(dataProvider);
        verify(isValidZipcode, times(ONCE)).execute(INVALID_ZIPCODE_LARGE);
    }

    @Test
    void shouldThrowInvalidZipcodeWithInvalidCharOnZipcode() {
        when(isValidZipcode.execute(INVALID_ZIPCODE_INVALID_CHAR)).thenThrow(InvalidZipcodeException.class);

        assertThatThrownBy(() -> useCase.execute(INVALID_ZIPCODE_INVALID_CHAR))
                .isInstanceOf(InvalidZipcodeException.class);

        verifyNoInteractions(dataProvider);
        verify(isValidZipcode, times(ONCE)).execute(INVALID_ZIPCODE_INVALID_CHAR);
    }

    @Test
    void shouldThrowInvalidZipcodeWithNullZipcode() {
        when(isValidZipcode.execute(NULL_ZIPCODE)).thenThrow(InvalidZipcodeException.class);

        assertThatThrownBy(() -> useCase.execute(NULL_ZIPCODE))
                .isInstanceOf(InvalidZipcodeException.class);

        verifyNoInteractions(dataProvider);
        verify(isValidZipcode, times(ONCE)).execute(NULL_ZIPCODE);
    }

    @Test
    void shouldThrowNotFoundWithInvalidZipcodeValidPattern() {
        when(isValidZipcode.execute(NOT_FOUND_ZIPCODE_WITH_DASH)).thenReturn(VALID_ZIPCODE);
        when(dataProvider.execute(NOT_FOUND_ZIPCODE_WITH_DASH)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(NOT_FOUND_ZIPCODE_WITH_DASH))
                .isInstanceOf(ZipcodeNotFoundException.class);

        verify(isValidZipcode, times(ONCE)).execute(NOT_FOUND_ZIPCODE_WITH_DASH);
        verify(dataProvider, times(ONCE)).execute(NOT_FOUND_ZIPCODE_WITH_DASH);
    }

    @Test
    void shouldThrowNotFoundWithInvalidZipcodeNoDashValidPattern() {
        when(isValidZipcode.execute(NOT_FOUND_ZIPCODE_WITHOUT_DASH)).thenReturn(VALID_ZIPCODE);
        when(dataProvider.execute(NOT_FOUND_ZIPCODE_WITHOUT_DASH)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(NOT_FOUND_ZIPCODE_WITHOUT_DASH))
                .isInstanceOf(ZipcodeNotFoundException.class);

        verify(isValidZipcode, times(ONCE)).execute(NOT_FOUND_ZIPCODE_WITHOUT_DASH);
        verify(dataProvider, times(ONCE)).execute(NOT_FOUND_ZIPCODE_WITHOUT_DASH);
    }

    @Test
    void shouldFoundZipcodeWithValidPattern() {
        when(isValidZipcode.execute(VALID_ZIPCODE_WITH_DASH)).thenReturn(VALID_ZIPCODE);
        when(dataProvider.execute(VALID_ZIPCODE_WITH_DASH)).thenReturn(FOUND_ZIPCODE);

        var zipcode = useCase.execute(VALID_ZIPCODE_WITH_DASH);
        assertEquals(FOUND_ZIPCODE.get(), zipcode);

        verify(isValidZipcode, times(ONCE)).execute(VALID_ZIPCODE_WITH_DASH);
        verify(dataProvider, times(ONCE)).execute(VALID_ZIPCODE_WITH_DASH);
    }

    @Test
    void shouldFoundZipcodeWithValidPatternNoDash() {
        when(isValidZipcode.execute(VALID_ZIPCODE_WITHOUT_DASH)).thenReturn(VALID_ZIPCODE);
        when(dataProvider.execute(VALID_ZIPCODE_WITHOUT_DASH)).thenReturn(FOUND_ZIPCODE);

        var zipcode = useCase.execute(VALID_ZIPCODE_WITHOUT_DASH);
        assertEquals(FOUND_ZIPCODE.get(), zipcode);

        verify(isValidZipcode, times(ONCE)).execute(VALID_ZIPCODE_WITHOUT_DASH);
        verify(dataProvider, times(ONCE)).execute(VALID_ZIPCODE_WITHOUT_DASH);
    }
}
