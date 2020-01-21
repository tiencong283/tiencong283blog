package io.tiencong283.blog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Entity
public class Post {
    private static final DateTimeFormatter SLUG_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

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

    @Column(nullable = true)
    private LocalDate publishDate;

    @Column(nullable = false)
    private boolean draft = true;

    @Column(nullable = false, columnDefinition = "text")
    private String rawContent;

    @Column(nullable = true, columnDefinition = "text")
    private String renderedContent;

    @Column(unique = true, nullable = true, length = 280)
    private String publicSlug;

    @Column(unique = true, nullable = true)
    private String adminSlug;

    public Post() {
    }

    public Post(String title, PostCategory category, PostFormat format, String rawContent) {
        setTitle(title);
        this.category = category;
        this.format = format;
        setRawContent(rawContent);
    }

    public void setTitle(String title) {
        this.title = StringUtils.trimWhitespace(title);
    }

    public void setRawContent(String rawContent) {
        this.rawContent = StringUtils.trimWhitespace(rawContent);
    }

    public void setRenderedContent(String renderedContent) {
        this.renderedContent = StringUtils.trimWhitespace(renderedContent);
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
        publicSlug = this.publishDate == null ? null : generatePublicSlug();
    }

    private String generatePublicSlug() {
        return String.format("%s/%s", getPublishDate().format(SLUG_DATE_FORMATTER), getSlug());
    }

    private String getSlug() {
        if (this.title == null)
            return "";
        // remove accents
        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/normalize
        StringBuilder decomposed = new StringBuilder(Normalizer.normalize(title, Normalizer.Form.NFD));
        int i = 0;
        while (i < decomposed.length()) {
            char c = decomposed.charAt(i);
            if (c == '\u0111') {    // remaining accent characters {đ and Đ}
                decomposed.setCharAt(i, 'd');
            } else if (c == '\u0110') {
                decomposed.setCharAt(i, 'D');
            }
            if (c > 0x7F) {   // exclude any non-ascii chars
                decomposed.deleteCharAt(i);
            } else {
                i += 1;
            }
        }

        // remove unnecessary characters
        String cleanTitle = decomposed.toString().replaceAll("([^a-zA-Z0-9]+)", "-").toLowerCase();
        return cleanTitle;
    }


    @Override
    public String toString() {
        return "Post{" +
                "postID=" + postID +
                ", title='" + title + '\'' +
                ", category=" + category +
                ", author=" + author +
                ", format=" + format +
                ", creationDate=" + creationDate +
                ", publishDate=" + publishDate +
                ", draft=" + draft +
                ", rawContent='" + rawContent + '\'' +
                ", renderedContent='" + renderedContent + '\'' +
                ", publicSlug='" + publicSlug + '\'' +
                ", adminSlug='" + adminSlug + '\'' +
                '}';
    }


}
