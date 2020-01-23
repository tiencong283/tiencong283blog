package io.tiencong283.service.markup;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MarkdownRenderer implements MarkupRenderer {
    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownRenderer() {
        List<Extension> extensions = Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                AutolinkExtension.create());

        // markup parser
        parser = Parser.builder()
                .extensions(extensions)
                .build();
        // markdown renderer
        renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .nodeRendererFactory(context -> new HeadingAnchorRenderer(context))
                .build();
    }
    @Override
    public String renderToHtml(String markup) {
        return renderer.render(parser.parse(markup));
    }

    @Override
    public boolean canRender(MediaType mediaType) {
        return mediaType.TEXT_MARKDOWN.isCompatibleWith(mediaType);
    }
}
