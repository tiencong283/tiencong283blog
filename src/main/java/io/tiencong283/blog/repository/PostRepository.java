package io.tiencong283.blog.repository;

import io.tiencong283.blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByDraftIsFalseAndPublicSlug(String publicSlug);

    Page<Post> findByDraftIsFalse(Pageable pageRequest);

    List<Post> findAllByDraftIsTrueOrderByCreationDateDesc();

    boolean existsByTitle(String title);
}
