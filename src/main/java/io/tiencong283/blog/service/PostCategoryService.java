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
    public void addCategory(PostCategory postCategory) {
        categoryRepo.save(postCategory);
        categoryRepo.flush();
    }

    public List<PostCategory> getAllCategories() {
        return categoryRepo.findAll();
    }

    public PostCategory getCategory(int categoryID) {
        PostCategory category = categoryRepo.getOne(categoryID);
        return category;
    }

    public boolean exists(String name) {
        return categoryRepo.existsByNameIgnoreCase(name);
    }

    public void deleteCategory(int categoryID) {
        categoryRepo.deleteById(categoryID);
        categoryRepo.flush();
    }

    public void updateCategory(PostCategory postCategory) {
        PostCategory update = getCategory(postCategory.getCategoryID());
        update.setName(postCategory.getName());
        update.setUrlSlug(postCategory.getUrlSlug());
        addCategory(update);
    }
}
