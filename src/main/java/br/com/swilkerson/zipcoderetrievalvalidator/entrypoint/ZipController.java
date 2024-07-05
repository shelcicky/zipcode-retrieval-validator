package br.com.swilkerson.zipcoderetrievalvalidator.entrypoint;

import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.usecase.RetrieverZipcodeUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipcode")
public class ZipController {
    private final RetrieverZipcodeUseCase useCase;

    public ZipController(RetrieverZipcodeUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/{zipcode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Zipcode> find(@PathVariable String zipcode) {
        return ResponseEntity.ok(useCase.execute(zipcode));
    }
}
