package br.com.swilkerson.zipcoderetrievalvalidator.domain.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ZipcodeValidator {
    private static final String PATTERN_ZIP_CODE = "^\\d{5}-?\\d{3}$";

    public boolean execute(String zipcode) {
        return StringUtils.isNotBlank(zipcode) && Pattern.compile(PATTERN_ZIP_CODE).matcher(zipcode).find();
    }
}
