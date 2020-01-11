package io.tiencong283.blog.controller;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.service.PostCategoryService;
import io.tiencong283.blog.service.PostService;
import io.tiencong283.blog.support.PageableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public BlogController(PostService postService, PostCategoryService postCategoryService, PageableFactory pageableFactory) {
        this.postService = postService;
        this.postCategoryService = postCategoryService;
        this.pageableFactory = pageableFactory;
    }
    @ModelAttribute
    public void populateCategories(Model model){
        List<PostCategory> allCategories = postCategoryService.getAllCategories();
        model.addAttribute("categories", allCategories);
    }

    @RequestMapping(value ="", method={GET, HEAD})
    public String showPosts(Model model, @RequestParam(value = "page", defaultValue = "1") int page){
        Slice<Post> allPublishedPosts = postService.getPublishedPosts(pageableFactory.forLists(page));

        model.addAttribute("posts", allPublishedPosts);
        return "index";
    }

    @RequestMapping(value ="{year:\\d+}/{month:\\d+}/{slug}", method={GET, HEAD})
    public String showPost(Model model, @PathVariable String year, @PathVariable String month, @PathVariable String slug){
        String publicSlug = String.format("%s/%s/%s", year, month, slug);
        Post publishedPost = postService.getPublishedPost(publicSlug);

        model.addAttribute("post", publishedPost);
        return "post-detail";
    }

    @RequestMapping(value ="{year:\\d+}/{month:\\d+}", method={GET, HEAD})
    public String showPostsByDate(Model model, @PathVariable int year, @PathVariable int month, @RequestParam(value = "page", defaultValue = "1") int page){
        Page<Post> posts = postService.getPublishedPostsByDate(year, month, pageableFactory.forLists(page));

        model.addAttribute("posts", posts);
        return "index";
    }

    @RequestMapping(value ="{year:\\d+}", method={GET, HEAD})
    public String showPostsByDate(Model model, @PathVariable int year, @RequestParam(value = "page", defaultValue = "1") int page){
        Page<Post> posts = postService.getPublishedPostsByDate(year, pageableFactory.forLists(page));

        model.addAttribute("posts", posts);
        return "index";
    }
}
