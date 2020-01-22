package io.tiencong283.blog.service;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.view.PostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PostFormAdapter {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Post createPostFromPostForm(PostForm postForm, String username) {
        Post post = new Post();
        post.setCreationDate(LocalDate.now());
        post.setAuthor(userService.loadUserByUsername(username));
        setCommonProperties(post, postForm);
        return post;
    }

    public Post updatePostFromPostForm(Post post, PostForm postForm) {
        setCommonProperties(post, postForm);
        return post;
    }

    private void setCommonProperties(Post post, PostForm postForm) {
        post.setTitle(postForm.getTitle());
        post.setCategory(postForm.getCategory());
        post.setFormat(postForm.getFormat());
        post.setRawContent(postForm.getRawContent());
        post.setDraft(postForm.isDraft());
        post.setPublishDate(publishDate(postForm));
    }

    private LocalDate publishDate(PostForm postForm) {
        if (postForm.isDraft()) {
            return null;
        }
        return postForm.getPublishDate() == null ? LocalDate.now() : postForm.getPublishDate();
    }
}
