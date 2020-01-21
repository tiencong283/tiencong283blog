package io.tiencong283.blog.support;

import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.model.PostFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Getter
@Setter
public class PostForm {
    @NotEmpty(message = "{empty}")
    private String title;
    private PostCategory category;
    private PostFormat format;
    @NotEmpty(message = "{empty}")
    private String rawContent;
    private boolean draft;
    private LocalDate publishDate;

    public PostForm() {
    }

    public void setTitle(String title) {
        this.title = StringUtils.trimWhitespace(title);
    }

    public void setRawContent(String rawContent) {
        this.rawContent = StringUtils.trimWhitespace(rawContent);
    }

    @Override
    public String toString() {
        return "PostForm{" +
                "title='" + title + '\'' +
                ", category=" + category +
                ", format=" + format +
                ", rawContent='" + rawContent + '\'' +
                ", draft=" + draft +
                ", publishDate=" + publishDate +
                '}';
    }
}
