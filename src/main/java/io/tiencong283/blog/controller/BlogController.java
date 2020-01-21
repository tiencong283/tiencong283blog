package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.service.PostCategoryService;
import io.tiencong283.blog.service.PostService;
import io.tiencong283.blog.support.PageableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@Controller
@RequestMapping("/blog")
public class BlogController {
    private PostService postService;
    private PostCategoryService postCategoryService;
    private PageableFactory pageableFactory;

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setPostCategoryService(PostCategoryService postCategoryService) {
        this.postCategoryService = postCategoryService;
    }

    @Autowired
    public void setPageableFactory(PageableFactory pageableFactory) {
        this.pageableFactory = pageableFactory;
    }

    @ModelAttribute
    public void populateCategories(Model model) {
        List<PostCategory> allCategories = postCategoryService.getAllCategories();
        model.addAttribute("categories", allCategories);
    }

    @RequestMapping(value = "", method = {GET, HEAD})
    public String showPosts(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Slice<Post> allPublishedPosts = postService.getPublishedPosts(pageableFactory.forLists(page));

        model.addAttribute("posts", allPublishedPosts);
        return "index";
    }
}
