package io.tiencong283.blog.service;

import io.tiencong283.blog.model.PostCategory;
import io.tiencong283.blog.repository.PostCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCategoryService {
    private PostCategoryRepository categoryRepo;

    @Autowired
    public PostCategoryService(PostCategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // query methods
    public void addCategory(PostCategory postCategory){
        categoryRepo.save(postCategory);
    }
    public List<PostCategory> getAllCategories(){
        return categoryRepo.findAll();
    }
}
