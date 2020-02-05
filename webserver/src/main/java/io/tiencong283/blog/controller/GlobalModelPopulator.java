package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.service.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalModelPopulator {
    private PostCategoryService postCategoryService;

    @Autowired
    public void setPostCategoryService(PostCategoryService postCategoryService) {
        this.postCategoryService = postCategoryService;
    }

    // category listing in header section so does every controllers must have one
    @ModelAttribute
    public void populateCategories(Model model) {
        List<PostCategory> categories = postCategoryService.getAllCategories();
        Collections.sort(categories);
        model.addAttribute("categories", categories);
    }
}
