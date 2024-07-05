package br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider;

import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;

import java.util.Optional;

public interface RetrieverZipcodeDataProvider {
    Optional<Zipcode> execute(String zipcode);
}
