package io.tiencong283.service.markup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * Render lightweight markup into HTML
 * from sagan projects
 */
@Controller
public class MarkupController {
    private final List<MarkupRenderer> converters;

    @Autowired
    public MarkupController(List<MarkupRenderer> converters) {
        this.converters = converters;
    }

    @PostMapping(path = "/documents", produces = "text/html")
    public ResponseEntity<String> renderMarkup(@RequestHeader("Content-Type") MediaType contentType,
                                               @RequestBody String markup) {
        return converters.stream()
                .filter(converter -> converter.canRender(contentType))
                .findFirst()
                .map(converter -> ResponseEntity.ok(converter.renderToHtml(markup)))
                .orElse(ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build());
    }
}