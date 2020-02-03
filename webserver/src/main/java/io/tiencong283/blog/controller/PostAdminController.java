package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.model.PostFormat;
import io.tiencong283.blog.service.PostCategoryService;
import io.tiencong283.blog.service.PostService;
import io.tiencong283.blog.support.PageableFactory;
import io.tiencong283.blog.validator.PostFormValidator;
import io.tiencong283.blog.view.PaginationInfo;
import io.tiencong283.blog.view.PostForm;
import io.tiencong283.blog.view.PostView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@Controller
@RequestMapping("/admin/blog/post")
public class PostAdminController {
    // servicess
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
    public void populateModel(Model model) {    // some common model attributes
        List<PostCategory> categories = categoryService.getAllCategories();
        Collections.sort(categories);
        model.addAttribute("categories", categories);
        model.addAttribute("formats", PostFormat.values());
    }
    // controllers
    @RequestMapping(path = "", method = {GET, HEAD})
    public String showDashboard(Model model, @RequestParam(defaultValue = "1") int page, HttpServletRequest request) {
        Page<PostView> publishedPosts = PostView.pageOf(postService.getPublishedPosts(pageableFactory.forDashboard(page)));
        PaginationInfo paginationInfo = new PaginationInfo(publishedPosts, request.getRequestURI());

        if (page == 1) {    // only show draft posts on main page
            model.addAttribute("draftPosts", PostView.listOf(postService.getAllDraftPosts()));
        }
        model.addAttribute("posts", publishedPosts);
        model.addAttribute("paginationInfo", paginationInfo);
        return "admin/post-index";
    }

    @PostMapping("")
    public String savePost(Principal principal, @Valid PostForm postForm, BindingResult errors) {
        postFormValidator.validate(postForm, errors);
        if (errors.hasErrors()) {
            return "admin/post-new";
        }
        // principal is different from (WebUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        // so casting to WebUser will be failed
        Post post = postService.addPost(postForm, principal.getName());
        return String.format("redirect:/admin/blog/post/%s/edit", post.getAdminSlug());
    }

    @RequestMapping(path = "/new", method = {GET, HEAD})
    public String showNewPostForm(Model model) {
        model.addAttribute("postForm", new PostForm());
        return "admin/post-new";
    }

    @RequestMapping(path = "/{postID:\\d+}{slug}/edit", method = {GET, HEAD})
    public String editPost(Model model, @PathVariable int postID, @PathVariable String slug) {
        PostView post = PostView.of(postService.getPost(postID));

        model.addAttribute("postForm", new PostForm(post));
        model.addAttribute("path", post.getUpdatePath());
        return "admin/post-edit";
    }

    @PostMapping("/{postID:\\d+}{slug}")
    public String updatePost(Model model, @PathVariable int postID, @PathVariable String slug, @Valid PostForm postForm, BindingResult errors) {
        PostView post = PostView.of(postService.getPost(postID));
        if (!postForm.getTitle().equals(post.getTitle())) { // avoid duplicate itself
            postFormValidator.validate(postForm, errors);
        }
        if (errors.hasErrors()) {
            return "admin/post-edit";
        }
        postService.updatePost(post.getPost(), postForm);
        return "redirect:" + post.getEditPath();
    }

    @PostMapping(path = "/{postID:\\d+}{slug}", params = "_action=DELETE")
    public String deletePost(@PathVariable int postID) {
        postService.deletePost(postID);
        return "redirect:"; // reload the post dashboard
    }
}
