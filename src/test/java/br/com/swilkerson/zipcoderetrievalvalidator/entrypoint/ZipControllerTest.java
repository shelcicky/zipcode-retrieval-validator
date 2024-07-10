package br.com.swilkerson.zipcoderetrievalvalidator.entrypoint;

import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions.InvalidZipcodeException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions.ZipcodeNotFoundException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.usecase.RetrieverZipcodeUseCase;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ZipController.class)
class ZipControllerTest {
    private static final int SUCCESS_RESPONSE_SIZE = 7;
    private static final String ROOT_JSON_PATH = "$.*";
    private static final String BASE_PATH = "/zipcode/";
    private static final String INVALID_ZIPCODE_SHORT = "1234567";
    private static final String INVALID_ZIPCODE_LARGE = "123456789";
    private static final String INVALID_ZIPCODE_INVALID_CHAR = "01001*000";
    private static final String NOT_FOUND_ZIPCODE_WITHOUT_DASH = "00000000";
    private static final String NOT_FOUND_ZIPCODE_WITH_DASH = "00000-000";
    private static final String VALID_ZIPCODE_WITH_DASH = "01001-000";
    private static final String VALID_ZIPCODE_WITHOUT_DASH = "01001000";
    private static final Zipcode VALID_RETURN = new Zipcode(VALID_ZIPCODE_WITH_DASH, "Praça da Sé",
            "lado ímpar", "", "Sé", "São Paulo", "SP");

    @MockBean
    private RetrieverZipcodeUseCase useCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnBadRequestWithShortZipcode() throws Exception {
        when(useCase.execute(INVALID_ZIPCODE_SHORT)).thenThrow(InvalidZipcodeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH.concat(INVALID_ZIPCODE_SHORT)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWithLargeZipcode() throws Exception {
        when(useCase.execute(INVALID_ZIPCODE_LARGE)).thenThrow(InvalidZipcodeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH.concat(INVALID_ZIPCODE_LARGE)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWithInvalidCharZipcode() throws Exception {
        when(useCase.execute(INVALID_ZIPCODE_INVALID_CHAR)).thenThrow(InvalidZipcodeException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH.concat(INVALID_ZIPCODE_INVALID_CHAR)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWithInvalidZipcodeValidZipPattern() throws Exception {
        when(useCase.execute(NOT_FOUND_ZIPCODE_WITHOUT_DASH)).thenThrow(ZipcodeNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH.concat(NOT_FOUND_ZIPCODE_WITHOUT_DASH)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWithDashInvalidZipcodeValidZipPattern() throws Exception {
        when(useCase.execute(NOT_FOUND_ZIPCODE_WITH_DASH)).thenThrow(ZipcodeNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH.concat(NOT_FOUND_ZIPCODE_WITH_DASH)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnSuccessWithValidZipcode() throws Exception {
        when(useCase.execute(VALID_ZIPCODE_WITH_DASH)).thenReturn(VALID_RETURN);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH.concat(VALID_ZIPCODE_WITH_DASH)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(ROOT_JSON_PATH, Matchers.hasSize(SUCCESS_RESPONSE_SIZE)));
    }

    @Test
    void shouldReturnSuccessWithValidZipcodeNoDash() throws Exception {
        when(useCase.execute(VALID_ZIPCODE_WITHOUT_DASH)).thenReturn(VALID_RETURN);
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH.concat(VALID_ZIPCODE_WITHOUT_DASH)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(ROOT_JSON_PATH, Matchers.hasSize(SUCCESS_RESPONSE_SIZE)));
    }
}