package io.tiencong283.blog.conversion;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    private final String postFormDateFormat = "M/dd/yyyy";

    /*
        mapping from date string to LocalDate
    */
    @Override
    public LocalDate parse(String s, Locale locale) throws ParseException {
        s = StringUtils.trimWhitespace(s);
        if (s == null || s.length() == 0) {  // empty publishDate allowed
            return null;
        }
        LocalDate parsed = null;
        try {
            parsed = LocalDate.parse(s, DateTimeFormatter.ofPattern(postFormDateFormat));
        } catch (Exception ignored) {
            throw new IllegalArgumentException(String.format("Localdate is not in valid format (%s)", postFormDateFormat));
        }
        return parsed;
    }

    // in rendering in client environment
    @Override
    public String print(LocalDate localDate, Locale locale) {
        return localDate.format(DateTimeFormatter.ofPattern(postFormDateFormat));
    }
}
