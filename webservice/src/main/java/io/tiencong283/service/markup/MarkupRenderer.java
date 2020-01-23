package io.tiencong283.service.markup;

import org.springframework.http.MediaType;

/**
 * Convert lightweight markup format into HTML
 * from sagan projects
 */

public interface MarkupRenderer {
    String renderToHtml(String markup);
    boolean canRender(MediaType mediaType);
}
