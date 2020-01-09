package io.tiencong283.blog.model;

import lombok.Getter;

@Getter
public enum PostFormat {
    MARKDOWN("Markdown", "markdown");

    private String displayName;
    private String urlSlug;

    PostFormat(String displayName, String urlSlug) {
        this.displayName = displayName;
        this.urlSlug = urlSlug;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
