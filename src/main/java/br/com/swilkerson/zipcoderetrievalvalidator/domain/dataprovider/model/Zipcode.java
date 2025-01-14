package br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Zipcode(@JsonAlias("cep") @JsonProperty("zip_code") String zipcode,
                      @JsonAlias("logradouro") @JsonProperty("street") String publicPlace,
                      @JsonAlias("complemento") @JsonProperty("complement") String complement,
                      @JsonAlias("unidade") @JsonProperty("unit") String unit,
                      @JsonAlias("bairro") @JsonProperty("neighborhood") String neighborhood,
                      @JsonAlias("localidade") @JsonProperty("city") String city,
                      @JsonAlias("uf") @JsonProperty("state") String state) {
}
