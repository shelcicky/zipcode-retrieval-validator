package br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.viacep;

import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ViaCepClient",
        url = "${integration.via-cep.url}",
        primary = false,
        configuration = {
                JacksonEncoder.class
        })
public interface ViaCepFeignClient {
        @GetMapping(path = "/ws/{zipcode}/json/", consumes = MediaType.APPLICATION_JSON_VALUE)
        ResponseEntity<Zipcode> retrieveZipcode(@PathVariable("zipcode") String zipcode);
}
