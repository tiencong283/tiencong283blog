package io.tiencong283.blog.conversion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConversionConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(localDateTimeFormatter());
    }

    @Bean
    public LocalDateTimeFormatter localDateTimeFormatter() {
        return new LocalDateTimeFormatter();
    }
}