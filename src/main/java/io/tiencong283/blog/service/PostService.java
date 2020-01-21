package io.tiencong283.blog.service;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.repository.PostRepository;
import io.tiencong283.blog.support.PostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private PostRepository postRepo;
    private PostFormAdapter postFormAdapter;

    @Autowired
    public void setPostFormAdapter(PostFormAdapter postFormAdapter) {
        this.postFormAdapter = postFormAdapter;
    }

    @Autowired
    public void setPostRepo(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    // get published post by its urlSlug
    public Post getPublishedPost(String publicSlug) {
        return postRepo.findByDraftIsFalseAndPublicSlug(publicSlug);
    }

    // get all published posts
    public Page<Post> getPublishedPosts(Pageable pageRequest) {
        return postRepo.findByDraftIsFalse(pageRequest);
    }

    // get all draft posts, order by creationDate
    public List<Post> getAllDraftPosts() {
        return postRepo.findAllByDraftIsTrueOrderByCreationDateDesc();
    }

    // return true if there're a post with title in the database
    public boolean existsByTitle(String title) {
        return postRepo.existsByTitle(title);
    }

    // add new post
    public void addPost(Post post) {
        postRepo.save(post);
    }

    // add new post from post form
    public void addPost(PostForm postForm, String username) {
        Post post = postFormAdapter.createPostFromPostForm(postForm, username);
        System.out.println(post);
    }
}
