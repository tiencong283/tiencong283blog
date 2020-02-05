package io.tiencong283.blog.service;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.view.PostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PostFormAdapter {
    private UserService userService;
    private MarkupRenderer markupRenderer;
    private PostSummary postSummary;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMarkupRenderer(MarkupRenderer markupRenderer) {
        this.markupRenderer = markupRenderer;
    }

    @Autowired
    public void setPostSummary(PostSummary postSummary) {
        this.postSummary = postSummary;
    }

    public Post createPostFromPostForm(PostForm postForm, String username) {
        Post post = new Post();
        post.setCreationDate(LocalDateTime.now());
        post.setAuthor(userService.loadUserByUsername(username));
        setCommonProperties(post, postForm);
        refreshPostContent(post);
        return post;
    }

    public Post updatePostFromPostForm(Post post, PostForm postForm) {
        setCommonProperties(post, postForm);
        if (!postForm.getRawContent().equals(post.getRawContent())) {
            refreshPostContent(post);
        }
        return post;
    }

    private void setCommonProperties(Post post, PostForm postForm) {
        post.setTitle(postForm.getTitle());
        post.setCategory(postForm.getCategory());
        post.setFormat(postForm.getFormat());
        post.setRawContent(postForm.getRawContent());
        post.setDraft(postForm.isDraft());
        // only set publish date for published posts
        if (!postForm.isDraft())
            setPublishDate(post, postForm);
    }

    private void setPublishDate(Post post, PostForm postForm) {
        if (postForm.getPublishDate() == null)
            post.setPublishDate(LocalDateTime.now());
            // in case users update the publish date
        else if (!post.getPublishDate().toLocalDate().isEqual(postForm.getPublishDate().toLocalDate()))
            postForm.setPublishDate(postForm.getPublishDate());
    }

    private void refreshPostContent(Post post) {
        post.setRenderedContent(markupRenderer.render(post.getRawContent(), post.getFormat()));
        post.setSummary(postSummary.forContent(post.getRenderedContent()));
    }
}