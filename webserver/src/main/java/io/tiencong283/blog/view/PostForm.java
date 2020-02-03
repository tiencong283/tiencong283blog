package io.tiencong283.blog.view;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.model.PostFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostForm {
    @NotEmpty(message = "{empty}")
    private String title;
    private PostCategory category;
    private PostFormat format;
    @NotEmpty(message = "{empty}")
    private String rawContent;
    private boolean draft = true;   // by default it's true
    private LocalDateTime publishDate;

    public PostForm() {
    }

    public PostForm(Post post) {
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.format = post.getFormat();
        this.rawContent = post.getRawContent();
        this.draft = post.isDraft();
        this.publishDate = post.getPublishDate();
    }

    public PostForm(PostView post) {
        this(post.getPost());
    }

    public void setTitle(String title) {
        this.title = StringUtils.trimWhitespace(title);
    }

    public void setRawContent(String rawContent) {
        this.rawContent = StringUtils.trimWhitespace(rawContent);
    }
}
