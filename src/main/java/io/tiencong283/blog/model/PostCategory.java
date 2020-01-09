package io.tiencong283.blog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class PostCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String urlSlug;

    public PostCategory() {
    }

    public PostCategory(String name, String urlSlug) {
        this.name = name;
        this.urlSlug = urlSlug;
    }
}
