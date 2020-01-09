package io.tiencong283.blog.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    @Column(unique = true, nullable = false, length = 280)
    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "categoryID")
    private PostCategory category;

    @ManyToOne(cascade = {CascadeType.PERSIST}, optional = false)
    @JoinColumn(name = "userID")    // to change the foreign column name
    private WebUser author;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostFormat format;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private LocalDate publishDate;

    @Column(nullable = false)
    private boolean draft = true;

    @Column(nullable = false, columnDefinition = "text")
    private String rawContent;

    @Column(nullable = false, columnDefinition = "text")
    private String renderedContent;

    @Column(unique = true, nullable = false, length = 280)
    private String publicSlug;

    @Column(unique = true, nullable = false)
    private String adminSlug;

    public Post() {
    }
}
