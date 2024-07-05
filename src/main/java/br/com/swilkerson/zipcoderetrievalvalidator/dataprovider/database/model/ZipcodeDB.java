package br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.database.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "ZIPCODE", indexes = {@Index(name = "idx01_zipcode", columnList = "COD_ZIP", unique = true)})
public class ZipcodeDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COD_ZIP", nullable = false, length = 8)
    private String zipcode;

    @Column(name = "DES_ADDRESS", nullable = false)
    private String publicPlace;

    @Column(name = "DES_COMPLEMENT")
    private String complement;

    @Column(name = "COD_UNIT")
    private String unit;

    @Column(name = "DES_NEIGHBORHOOD", nullable = false)
    private String neighborhood;

    @Column(name = "DES_CITY", nullable = false)
    private String city;

    @Column(name = "COD_UF", nullable = false, length = 2)
    private String state;

    private ZipcodeDB() {
    }

    private ZipcodeDB(Long id, String zipcode, String publicPlace, String complement, String unit, String neighborhood,
                     String city, String state) {
        this.id = id;
        this.zipcode = zipcode;
        this.publicPlace = publicPlace;
        this.complement = complement;
        this.unit = unit;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getPublicPlace() {
        return publicPlace;
    }

    public String getComplement() {
        return complement;
    }

    public String getUnit() {
        return unit;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String zipcode;
        private String publicPlace;
        private String complement;
        private String unit;
        private String neighborhood;
        private String city;
        private String state;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withZipcode(String zipcode) {
            this.zipcode = zipcode;
            return this;
        }

        public Builder withPublicPlace(String publicPlace) {
            this.publicPlace = publicPlace;
            return this;
        }

        public Builder withComplement(String complement) {
            this.complement = complement;
            return this;
        }

        public Builder withUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder withNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withState(String state) {
            this.state = state;
            return this;
        }

        public ZipcodeDB build() {
            return new ZipcodeDB(this.id, this.zipcode, this.publicPlace, this.complement, this.unit, this.neighborhood,
                    this.city, this.state);
        }
    }
}
