package br.com.swilkerson.zipcoderetrievalvalidator.dataprovider.utils;

import org.springframework.stereotype.Component;

@Component
public class ZipcodeFormatter {
    private static final String SEPARATOR = "-";
    private static final String NOTHING = "";

    private static final char DASH = '-';
    private static final short DASH_INDEX = 5;

    public String removeDash(String zipcode) {
        return zipcode.replace(SEPARATOR, NOTHING);
    }

    public String addDash(String zipcode) {
        var sb = new StringBuilder(zipcode);
        sb.insert(DASH_INDEX, DASH);

        return sb.toString();
    }
}
