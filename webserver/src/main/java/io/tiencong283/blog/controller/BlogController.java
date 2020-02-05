package io.tiencong283.blog.controller;

import io.tiencong283.blog.service.PostCategoryService;
import io.tiencong283.blog.service.PostService;
import io.tiencong283.blog.support.PageableFactory;
import io.tiencong283.blog.view.PaginationInfo;
import io.tiencong283.blog.view.PostView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

@Controller
public class BlogController {
    // services
    private PostCategoryService postCategoryService;
    private PostService postService;
    private PageableFactory pageableFactory;

    @Autowired
    public void setPostCategoryService(PostCategoryService postCategoryService) {
        this.postCategoryService = postCategoryService;
    }

    @Autowired
    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    @Autowired
    public void setPageableFactory(PageableFactory pageableFactory) {
        this.pageableFactory = pageableFactory;
    }


    // controllers
    @RequestMapping(path = "", method = {GET, HEAD})
    public String showPosts(Model model, @RequestParam(defaultValue = "1") int page, HttpServletRequest request) {
        Page<PostView> posts = PostView.pageOf(postService.getPublishedPosts(pageableFactory.forLists(page)));
        model.addAttribute("posts", posts);
        model.addAttribute("paginationInfo", new PaginationInfo(posts, request.getRequestURI()));
        return "index";
    }

    @RequestMapping(path = "{year:\\d+}/{month:\\d+}/{day:\\d+}/{slug}", method = {GET, HEAD})
    public String showPost(Model model, @PathVariable String year, @PathVariable String month, @PathVariable String day, @PathVariable String slug){
        String publicSlug = String.format("%s/%s/%s/%s", year, month, day, slug);
        PostView post = PostView.of(postService.getPublishedPost(publicSlug));
        model.addAttribute("post", post);
        return "post";
    }

}
