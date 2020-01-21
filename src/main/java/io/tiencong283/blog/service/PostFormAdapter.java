package io.tiencong283.blog.service;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.model.WebUser;
import io.tiencong283.blog.support.PostForm;
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
        Post post = new Post(postForm.getTitle(), postForm.getCategory(), postForm.getFormat(), postForm.getRawContent());
        WebUser authUser = userService.loadUserByUsername(username);
        post.setAuthor(authUser);
        post.setCreationDate(LocalDate.now());
        return post;
    }
}
