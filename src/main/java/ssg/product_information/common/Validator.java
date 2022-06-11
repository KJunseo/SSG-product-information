package ssg.product_information.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ssg.product_information.exception.item.DateFormatException;

public class Validator {

    private Validator() {
    }

    public static void validatesDateFormat(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DateFormatException();
        }
    }
}
