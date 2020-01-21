package io.tiencong283.blog.validator;

import io.tiencong283.blog.service.PostService;
import io.tiencong283.blog.support.PostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostFormValidator implements Validator {
    private PostService postService;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PostForm.class.equals(aClass.getClass());
    }

    @Override
    public void validate(Object o, Errors errors) {
        PostForm postForm = (PostForm) o;
        if (!errors.hasFieldErrors("title") && postService.existsByTitle(postForm.getTitle())) { // distinct post's title
            errors.reject("title", "postForm.title.dupplicate");
        }
    }
}
