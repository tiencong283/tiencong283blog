package io.tiencong283.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class PostCategory implements Comparable<PostCategory>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String urlSlug;

    // bidirectional mapping
    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Post> posts = new ArrayList<>();

    public PostCategory() {
    }

    public PostCategory(String name) {
        this.setName(name);
        this.setUrlSlug(name);
    }

    public PostCategory(String name, String urlSlug) {
        this.setName(name);
        this.setUrlSlug(urlSlug);
    }

    public int countPosts(){
        return posts.size();
    }

    public void setName(String name) {
        this.name = StringUtils.trimWhitespace(name);
    }

    public void setUrlSlug(String urlSlug) {
        this.urlSlug = StringUtils.trimWhitespace(urlSlug);
    }

    @Override
    public String toString() {
        return String.format("PostCategory{id: %d, name: '%s', urlSlug: '%s'}", this.categoryID, this.name, this.urlSlug);
    }

    @Override
    public int compareTo(PostCategory postCategory) {
        return this.categoryID - postCategory.categoryID;
    }
}
