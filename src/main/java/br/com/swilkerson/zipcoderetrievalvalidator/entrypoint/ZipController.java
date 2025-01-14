package br.com.swilkerson.zipcoderetrievalvalidator.entrypoint;

import br.com.swilkerson.zipcoderetrievalvalidator.config.exceptions.model.DefaultException;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.usecase.RetrieverZipcodeUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/zipcode")
@Tag(name = "Zipcode Controller", description = "Controller for retrieve zipcode information")
public class ZipController {
    private final RetrieverZipcodeUseCase useCase;

    public ZipController(RetrieverZipcodeUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/{zipcode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Zipcode.class))),
            @ApiResponse(responseCode = "400", description = "Invalid zipcode format",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultException.class))),
            @ApiResponse(responseCode = "404", description = "Zipcode not found or not exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultException.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DefaultException.class)))
    })
    public ResponseEntity<Zipcode> find(@PathVariable String zipcode) {
        return ResponseEntity.ok(useCase.execute(zipcode));
    }
}
