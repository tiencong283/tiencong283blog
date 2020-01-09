package io.tiencong283.blog.repository;

import io.tiencong283.blog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Post findByDraftIsFalseAndPublicSlug(String publicSlug);
    Slice<Post> findByDraftIsFalse(Pageable pageRequest);
}
