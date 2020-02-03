package io.tiencong283.blog.conversion;

import org.springframework.format.Formatter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    private final String INPUT_DATETIME_FORMAT = "M/d/yyyy";
    private final String OUTPUT_DATETIME_FORMAT = "MM/dd/yyyy";

    /*
        mapping from date string to LocalDate
    */
    @Override
    public LocalDateTime parse(String s, Locale locale) throws ParseException {
        s = StringUtils.trimWhitespace(s);
        if (s == null || s.length() == 0) {  // empty publishDate allowed
            return null;
        }
        LocalDateTime parsed = null;

        try {
            parsed = LocalDate.parse(s, DateTimeFormatter.ofPattern(INPUT_DATETIME_FORMAT)).atStartOfDay();
        } catch (Exception ignored) {
            throw new IllegalArgumentException(String.format("Localdate is not in valid format (%s)", INPUT_DATETIME_FORMAT));
        }
        return parsed;
    }

    // in rendering in client environment
    @Override
    public String print(LocalDateTime localDateTime, Locale locale) {
        return localDateTime.format(DateTimeFormatter.ofPattern(OUTPUT_DATETIME_FORMAT));
    }
}
