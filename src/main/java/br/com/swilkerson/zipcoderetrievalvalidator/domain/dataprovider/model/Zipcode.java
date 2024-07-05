package br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Zipcode(@JsonProperty("cep") String zipcode,
                      @JsonProperty("logradouro") String publicPlace,
                      @JsonProperty("complemento") String complement,
                      @JsonProperty("unidade") String unit,
                      @JsonProperty("bairro") String neighborhood,
                      @JsonProperty("localidade") String city,
                      @JsonProperty("uf") String state) {
}
