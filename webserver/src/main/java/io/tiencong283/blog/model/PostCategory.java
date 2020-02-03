package io.tiencong283.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class PostCategory implements Comparable<PostCategory> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String urlSlug;

    // bidirectional mapping
    @JsonIgnore
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
        this.setUrlSlug(generateUrlSlug(name));
    }
    public String getPublicPath(){
        return "/category/" + this.urlSlug;
    }

    public int countPosts() {
        return posts.size();
    }

    public void setName(String name) {
        this.name = StringUtils.trimWhitespace(name);
    }

    public void setUrlSlug(String urlSlug) {
        this.urlSlug = StringUtils.trimWhitespace(urlSlug);
    }
    // return the string with spaces replaced by hyphen (windows programming -> windows-programming)
    public String generateUrlSlug(String name){
        return String.join("-", name.split("\\s+"));
    }
    @Override
    public int compareTo(PostCategory postCategory) {
        return this.categoryID - postCategory.categoryID;   // sort by categoryID
    }
}
