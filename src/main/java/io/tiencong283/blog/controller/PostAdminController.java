package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.model.PostFormat;
import io.tiencong283.blog.service.PostCategoryService;
import io.tiencong283.blog.service.PostService;
import io.tiencong283.blog.support.PageableFactory;
import io.tiencong283.blog.support.PostForm;
import io.tiencong283.blog.validator.PostFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;


@Controller
@RequestMapping("/admin/blog/post")
public class PostAdminController {
    private PostService postService;
    private PostCategoryService categoryService;
    private PostFormValidator postFormValidator;
    private PageableFactory pageableFactory;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setCategoryService(PostCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setPageableFactory(PageableFactory pageableFactory) {
        this.pageableFactory = pageableFactory;
    }

    @Autowired
    public void setPostFormValidator(PostFormValidator postFormValidator) {
        this.postFormValidator = postFormValidator;
    }

    @ModelAttribute
    public void populateModel(Model model) {
        List<PostCategory> categories = categoryService.getAllCategories();
        Collections.sort(categories);
        model.addAttribute("categories", categories);
        model.addAttribute("formats", PostFormat.values());
    }

    @RequestMapping(value = "", method = {GET, HEAD})
    public String showDashboard(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        Page<Post> publishedPosts = postService.getPublishedPosts(pageableFactory.forDashboard(page));
        if (page == 1) {
            model.addAttribute("draftPosts", postService.getAllDraftPosts());
        }
        model.addAttribute("posts", publishedPosts);
        return "admin/post-index";
    }

    @RequestMapping(value = "", method = POST)
    public String savePost(Principal principal, @Valid @ModelAttribute PostForm postForm, BindingResult errors) {
        postFormValidator.validate(postForm, errors);
        if (errors.hasErrors()) {
            return "admin/post-new";
        }
        // principal is different from (WebUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        // so casting to WebUser will be failed
        postService.addPost(postForm, principal.getName());
        return "admin/post-new";
    }

    @RequestMapping(value = "/new", method = {GET, HEAD})
    public String showNewPostForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "admin/post-new";
    }
}
