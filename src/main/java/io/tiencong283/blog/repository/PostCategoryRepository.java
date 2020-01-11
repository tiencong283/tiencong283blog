package io.tiencong283.blog.repository;

import io.tiencong283.blog.model.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Integer> {
    boolean existsByNameIgnoreCase(String name);
}
