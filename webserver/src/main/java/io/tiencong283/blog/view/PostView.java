package io.tiencong283.blog.view;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.model.PostFormat;
import io.tiencong283.blog.model.WebUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PostView {
    private final DateTimeFormatter postDateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
    private Post post;

    public PostView(Post post) {
        this.post = post;
    }

    public static PostView of(Post post) {
        return new PostView(post);
    }

    // convert Page<Post> to Page<PostView>
    public static Page<PostView> pageOf(Page<Post> posts) {
        List<PostView> postViews = posts.getContent().stream()
                .map(post -> PostView.of(post)).collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(posts.getNumber(), posts.getSize(), posts.getSort());
        return new PageImpl<>(postViews, pageRequest, posts.getTotalElements());
    }

    public static List<PostView> listOf(List<Post> posts) {
        return posts.stream()
                .map(post -> PostView.of(post)).collect(Collectors.toList());
    }

    // preview path
    public String getPath() {
        return "";
    }

    // edit post absolute path
    public String getEditPath() {
        return String.format("/admin/blog/post/%s/edit", getAdminSlug());
    }

    // delete post absolute path
    public String getUpdatePath() {
        return "/admin/blog/post/" + getAdminSlug();
    }

    // format dates
    public String getFormattedCreationDate() {
        return getCreationDate().format(postDateFormatter);
    }

    public String getFormattedPublishDate() {
        return getPublishDate().format(postDateFormatter);
    }

    // PostView is wrapper of post with some centralized config infos
    public String getTitle() {
        return post.getTitle();
    }

    public PostCategory getCategory() {
        return post.getCategory();
    }

    public WebUser getAuthor() {
        return post.getAuthor();
    }

    public PostFormat getFormat() {
        return post.getFormat();
    }

    public LocalDate getCreationDate() {
        return post.getCreationDate();
    }

    public LocalDate getPublishDate() {
        return post.getPublishDate();
    }

    public String getRawContent() {
        return post.getRawContent();
    }

    public String getPublicSlug() {
        return post.getPublicSlug();
    }

    public String getAdminSlug() {
        return post.getAdminSlug();
    }
}