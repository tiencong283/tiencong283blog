package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.service.PostCategoryService;
import io.tiencong283.blog.validator.PostCategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@RequestMapping("/admin/blog")
public class AdminController {
    private PostCategoryService postCategoryService;
    private PostCategoryValidator categoryValidator;

    @Autowired
    public AdminController(PostCategoryService postCategoryService, PostCategoryValidator categoryValidator) {
        this.postCategoryService = postCategoryService;
        this.categoryValidator = categoryValidator;
    }

    @ModelAttribute
    public void populateCategories(Model model){
        List<PostCategory> allCategories = postCategoryService.getAllCategories();
        Collections.sort(allCategories);
        model.addAttribute("categories", allCategories);
    }

    @RequestMapping(value = "/category", method = {GET, HEAD})
    public String showCategoryDashboard(Model model){
        model.addAttribute("category", new PostCategory());
        return "admin/category";
    }

    @RequestMapping(value = "/category", method = {POST})
    public String addCategory(@ModelAttribute @Valid PostCategory postCategory, BindingResult errors){
        categoryValidator.validate(postCategory, errors);
        if (errors.hasErrors()){
            // future
            return "redirect:category";
        }
        postCategoryService.addCategory(postCategory);
        return "redirect:category";
    }

    @RequestMapping(value = "/category/{categoryID:\\d+}", method = {GET, HEAD}, produces = "application/json")
    @ResponseBody
    public PostCategory getCategory(@PathVariable int categoryID){
        return postCategoryService.getCategory(categoryID);
    }

    @RequestMapping(value = "/category/{categoryID:\\d+}", method = {PUT})
    @ResponseBody
    public void editCategory(@PathVariable int categoryID, @ModelAttribute PostCategory postCategory){
        postCategory.setCategoryID(categoryID);
        postCategoryService.updateCategory(postCategory);
    }

    @RequestMapping(value = "/category/{categoryID:\\d+}", method = {DELETE})
    @ResponseBody
    public void deleteCategory(@PathVariable int categoryID){
        postCategoryService.deleteCategory(categoryID);
    }
}
