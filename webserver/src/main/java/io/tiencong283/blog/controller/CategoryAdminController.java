package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.service.PostCategoryService;
import io.tiencong283.blog.validator.CategoryFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@Controller
@RequestMapping("/admin/blog/category")
public class CategoryAdminController {
    private PostCategoryService postCategoryService;
    private CategoryFormValidator categoryValidator;

    @Autowired
    public CategoryAdminController(PostCategoryService postCategoryService, CategoryFormValidator categoryValidator) {
        this.postCategoryService = postCategoryService;
        this.categoryValidator = categoryValidator;
    }

    @ModelAttribute
    public void populateCategories(Model model) {
        List<PostCategory> allCategories = postCategoryService.getAllCategories();
        Collections.sort(allCategories);
        model.addAttribute("categories", allCategories);
    }

    @RequestMapping(value = "", method = {GET, HEAD})
    public String showCategoryDashboard(Model model) {
        model.addAttribute("category", new PostCategory());
        return "admin/category-index";
    }

    @PostMapping("")
    public String addCategory(@ModelAttribute @Valid PostCategory postCategory, BindingResult errors) {
        categoryValidator.validate(postCategory, errors);
        if (errors.hasErrors()) {
            // future
            return "redirect:category";
        }
        postCategoryService.addCategory(postCategory);
        return "redirect:category";
    }

    @RequestMapping(value = "/{categoryID:\\d+}", method = {GET, HEAD})
    @ResponseBody
    public PostCategory getCategory(@PathVariable int categoryID) {
        return postCategoryService.getCategory(categoryID);
    }

    @PutMapping("/{categoryID:\\d+}")
    @ResponseBody
    public void editCategory(@PathVariable int categoryID, @ModelAttribute PostCategory postCategory) {
        postCategory.setCategoryID(categoryID);
        postCategoryService.updateCategory(postCategory);
    }

    @DeleteMapping("/{categoryID:\\d+}")
    @ResponseBody
    public void deleteCategory(@PathVariable int categoryID) {
        postCategoryService.deleteCategory(categoryID);
    }
}
