package br.com.swilkerson.zipcoderetrievalvalidator.dataprovider;

import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.database.ZipCodeRepository;
import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.database.model.ZipcodeDB;
import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.utils.ZipcodeFormatter;
import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.viacep.ViaCepFeignClient;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;
import org.junit.jupiter.api.BeforeEach;
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
        RetrieverZipcodeDataProviderImpl.class
})
class RetrieverZipcodeDataProviderImplTest {
    private static final byte ONCE = 1;
    private static final String NULL_ZIPCODE = null;
    private static final String VALID_ZIPCODE_WITH_DASH = "01001-000";
    private static final String VALID_ZIPCODE_WITHOUT_DASH = "01001000";
    private static final String VALID_ZIPCODE_CASE2_WITH_DASH = "03210-000";
    private static final String VALID_ZIPCODE_CASE2_NO_DASH = "03210000";

    private static final Optional<ZipcodeDB> ZIPCODE = Optional.of(ZipcodeDB.builder()
            .withZipcode(VALID_ZIPCODE_WITHOUT_DASH)
            .withPublicPlace("Praça da Sé")
            .withComplement("lado ímpar")
            .withUnit("")
            .withNeighborhood("Sé")
            .withCity("São Paulo")
            .withState("SP")
            .build());

    private static final Optional<Zipcode> DOMAIN_ZIPCODE = Optional.of(new Zipcode(VALID_ZIPCODE_WITH_DASH,
            "Praça da Sé", "lado ímpar", "", "Sé", "São Paulo", "SP"));


    @MockBean
    private ZipCodeRepository repository;

    @MockBean
    private ViaCepFeignClient feignClient;

    @MockBean
    private ZipcodeFormatter zipcodeFormatter;

    @Autowired
    private RetrieverZipcodeDataProviderImpl dataProvider;

    @BeforeEach
    void setUp() {
        when(zipcodeFormatter.removeDash(VALID_ZIPCODE_WITH_DASH)).thenReturn(VALID_ZIPCODE_WITHOUT_DASH);
        when(zipcodeFormatter.removeDash(VALID_ZIPCODE_WITHOUT_DASH)).thenReturn(VALID_ZIPCODE_WITHOUT_DASH);
        when(zipcodeFormatter.removeDash(VALID_ZIPCODE_CASE2_NO_DASH)).thenReturn(VALID_ZIPCODE_CASE2_NO_DASH);
        when(zipcodeFormatter.addDash(VALID_ZIPCODE_WITHOUT_DASH)).thenReturn(VALID_ZIPCODE_WITH_DASH);
        when(zipcodeFormatter.addDash(VALID_ZIPCODE_CASE2_NO_DASH)).thenReturn(VALID_ZIPCODE_CASE2_WITH_DASH);
        when(repository.findByZipcode(VALID_ZIPCODE_WITHOUT_DASH)).thenReturn(ZIPCODE);
    }

    @Test
    void shouldThrowNullPointerWithNullZipcode() {
        assertThatThrownBy(() -> dataProvider.execute(NULL_ZIPCODE)).isInstanceOf(NullPointerException.class);

        verifyNoInteractions(feignClient);
        verifyNoInteractions(repository);
        verifyNoInteractions(zipcodeFormatter);
    }

    @Test
    void shouldReturnDomainZipcodeAndNoCallFeignClient() {
        assertEquals(DOMAIN_ZIPCODE, dataProvider.execute(VALID_ZIPCODE_WITH_DASH));
        verifyNoInteractions(feignClient);
        verify(zipcodeFormatter, times(ONCE)).removeDash(VALID_ZIPCODE_WITH_DASH);
        verify(zipcodeFormatter, times(ONCE)).addDash(VALID_ZIPCODE_WITHOUT_DASH);
    }

    @Test
    void shouldReturnDomainZipcodeNoDashAndNoCallFeignClient() {
        assertEquals(DOMAIN_ZIPCODE, dataProvider.execute(VALID_ZIPCODE_WITHOUT_DASH));
        verifyNoInteractions(feignClient);
        verify(zipcodeFormatter, times(ONCE)).removeDash(VALID_ZIPCODE_WITHOUT_DASH);
        verify(zipcodeFormatter, times(ONCE)).addDash(VALID_ZIPCODE_WITHOUT_DASH);
    }
}