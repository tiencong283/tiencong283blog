package io.tiencong283.blog.model;

import lombok.Getter;

@Getter
public enum PostFormat {
    MARKDOWN("Markdown", "markdown"),
    ASCIIDOC("Asciidoc", "asciidoc");

    private String displayName;
    private String urlSlug;

    PostFormat(String displayName, String urlSlug) {
        this.displayName = displayName;
        this.urlSlug = urlSlug;
    }

    public String getId() {
        return name();
    }
}
