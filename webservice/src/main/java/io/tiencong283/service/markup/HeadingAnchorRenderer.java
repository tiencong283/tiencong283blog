package io.tiencong283.service.markup;

import org.commonmark.node.Heading;
import org.commonmark.node.Link;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.HtmlNodeRendererContext;
import org.commonmark.renderer.html.HtmlWriter;
import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.util.Collections;
import java.util.Set;

public class HeadingAnchorRenderer implements NodeRenderer {
    private HtmlNodeRendererContext context;

    public HeadingAnchorRenderer(HtmlNodeRendererContext context) {
        this.context = context;
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        // return the node types we want to use this renderer for
        return Collections.singleton(Heading.class);
    }

    @Override
    public void render(Node node) {
        Heading heading = (Heading)node;
        HtmlWriter writer = context.getWriter();
        String hTag = "h" + heading.getLevel();
        String title = ((Text) heading.getFirstChild()).getLiteral();

        writer.tag(hTag);
        context.render(new Link("#" + getSlug(StringUtils.trimWhitespace(title)), null));
        context.render(heading.getFirstChild());
        writer.tag("/" + hTag);
        writer.line();
    }


    private String getSlug(String title) {
        if (title == null)
            return "";
        // remove accents
        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/normalize
        StringBuilder decomposed = new StringBuilder(Normalizer.normalize(title, Normalizer.Form.NFD));
        int i = 0;
        while (i < decomposed.length()) {
            char c = decomposed.charAt(i);
            if (c == '\u0111') {    // remaining accent characters {đ and Đ}
                decomposed.setCharAt(i, 'd');
            } else if (c == '\u0110') {
                decomposed.setCharAt(i, 'D');
            }
            if (c > 0x7F) {   // exclude any non-ascii chars
                decomposed.deleteCharAt(i);
            } else {
                i += 1;
            }
        }

        // remove unnecessary characters
        String cleanTitle = decomposed.toString().replaceAll("([^a-zA-Z0-9]+)", "-").toLowerCase();
        if (cleanTitle.charAt(cleanTitle.length() - 1) == '-')
            return cleanTitle.substring(0, cleanTitle.length() - 1);
        return cleanTitle;
    }
}
