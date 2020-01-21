package io.tiencong283.blog.conversion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Value("{localDateFormat}")
    private final String localDateFormat = "M/dd/yyyy";

    // for converting from string to LocalDate in forms
    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        s = StringUtils.trimWhitespace(s);
        if (s == null || s.length() == 0){  // empty publishDate allowed
            return null;
        }
        LocalDate parsed = null;
        try{
            parsed  = LocalDate.parse(s, DateTimeFormatter.ofPattern(localDateFormat));
        } catch (Exception ignored) {
            throw new IllegalArgumentException(String.format("Localdate is not in valid format (%s)", localDateFormat));
        }
        return parsed;
    }

    // in rendering in client environment
    @Override
    public String print(LocalDate localDate, Locale locale) {
        return localDate.format(DateTimeFormatter.ofPattern(localDateFormat));
    }
}
