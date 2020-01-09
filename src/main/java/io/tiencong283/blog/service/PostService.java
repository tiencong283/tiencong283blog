package io.tiencong283.blog.service;

import io.tiencong283.blog.model.Post;
import io.tiencong283.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private PostRepository postRepo;

    @Autowired
    public void setPostRepo(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public Post getPublishedPost(String publicSlug){
        return postRepo.findByDraftIsFalseAndPublicSlug(publicSlug);
    }

    public Slice<Post> getPublishedPosts(Pageable pageRequest){
        return postRepo.findByDraftIsFalse(pageRequest);
    }

    public Page<Post> getPublishedPostsByDate(int year, Pageable pageRequest){
        return null;
    }

    public Page<Post> getPublishedPostsByDate(int year, int month, Pageable pageRequest){
        return null;
    }
}
