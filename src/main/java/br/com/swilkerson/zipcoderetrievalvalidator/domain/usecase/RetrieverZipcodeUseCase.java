package br.com.swilkerson.zipcoderetrievalvalidator.domain.usecase;

import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.RetrieverZipcodeDataProvider;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions.InvalidZipcodeException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.exceptions.ZipcodeNotFoundException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.services.ZipcodeValidator;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RetrieverZipcodeUseCase {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ZipcodeValidator zipcodeValidator;
    private final RetrieverZipcodeDataProvider dataProvider;

    public RetrieverZipcodeUseCase(ZipcodeValidator zipcodeValidator, RetrieverZipcodeDataProvider dataProvider) {
        this.zipcodeValidator = zipcodeValidator;
        this.dataProvider = dataProvider;
    }

    public Zipcode execute(String zipcode) {
        if(BooleanUtils.negate(zipcodeValidator.execute(zipcode))) {
            log.warn("m=execute, '{}' is not a valid zipcode!", zipcode);
            throw new InvalidZipcodeException(zipcode);
        }

        return dataProvider.execute(zipcode)
                .orElseThrow(() -> new ZipcodeNotFoundException(zipcode));
    }
}
