package br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.database;

import br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.database.model.ZipcodeDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipcodeDB, Long> {
    Optional<ZipcodeDB> findByZipcode(String zipcode);
}
