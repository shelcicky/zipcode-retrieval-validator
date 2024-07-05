package br.com.swilkerson.zipcoderetrievalvalidator.dataprovider;

import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.database.ZipCodeRepository;
import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.database.model.ZipcodeDB;
import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.utils.ZipcodeFormatter;
import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.viacep.ViaCepFeignClient;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.RetrieverZipcodeDataProvider;
import br.com.swilkerson.zipcoderetrievalvalidator.domain.dataprovider.model.Zipcode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class RetrieverZipcodeDataProviderImpl implements RetrieverZipcodeDataProvider {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ZipCodeRepository repository;
    private final ViaCepFeignClient feignClient;
    private final ZipcodeFormatter zipcodeFormatter;

    public RetrieverZipcodeDataProviderImpl(ZipCodeRepository repository,
                                            ViaCepFeignClient feignClient,
                                            ZipcodeFormatter zipcodeFormatter) {
        this.repository = repository;
        this.feignClient = feignClient;
        this.zipcodeFormatter = zipcodeFormatter;
    }

    @Override
    public Optional<Zipcode> execute(String zipcode) {
        Objects.requireNonNull(zipcode);
        var formattedZipcode = zipcodeFormatter.removeDash(zipcode);
        var zipDb = repository.findByZipcode(formattedZipcode);

        if(zipDb.isPresent()){
            log.info("m=execute, msg: zipcode '{}' retrieved from database", formattedZipcode);
            return toDomain(zipDb.get());
        }

        return retrieveFromExternalData(formattedZipcode);
    }

    private Optional<Zipcode> toDomain(ZipcodeDB row) {
        return Optional.of(
                new Zipcode(
                        zipcodeFormatter.addDash(row.getZipcode()),
                        row.getPublicPlace(),
                        row.getComplement(),
                        row.getUnit(),
                        row.getNeighborhood(),
                        row.getCity(),
                        row.getState()));
    }

    private ZipcodeDB toDataProvider(Zipcode zipcode) {
        return ZipcodeDB.builder()
                .withZipcode(zipcodeFormatter.removeDash(zipcode.zipcode()))
                .withPublicPlace(zipcode.publicPlace())
                .withComplement(zipcode.complement())
                .withUnit(zipcode.unit())
                .withNeighborhood(zipcode.neighborhood())
                .withCity(zipcode.city())
                .withState(zipcode.state())
                .build();
    }

    private Optional<Zipcode> retrieveFromExternalData(String zipcode) {
        try {
            var zip = feignClient.retrieveZipcode(zipcode).getBody();
            if(Objects.isNull(zip) || Objects.isNull(zip.zipcode())) {
                log.warn("m=execute, msg: zipcode '{}' was not found on external service.", zipcode);
                return Optional.empty();
            }

            log.info("m=execute, msg: zipcode '{}' retrieved from external service.", zipcode);
            return toDomain(repository.save(toDataProvider(zip)));
        } catch(Exception e) {
            log.error("m=execute, failed to retriever zipcode='{}' from external service, e='{}', msg='{}'", zipcode,
                    e.getClass().getSimpleName(), e.getMessage());
            return Optional.empty();
        }
    }
}
