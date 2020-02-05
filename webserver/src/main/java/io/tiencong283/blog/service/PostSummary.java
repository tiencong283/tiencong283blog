package io.tiencong283.blog.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class PostSummary {
    private final int SUMMARY_LENGTH = 800;

    public String forContent(String content) {
        return forContent(content, SUMMARY_LENGTH);
    }

    // return the first portion of the post content
    public String forContent(String content, int maxLength) {
        Document document = Jsoup.parse(content);
        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (Element element : document.body().children()) {
            builder.append(element.outerHtml());
            builder.append("\n");
            if (!element.tagName().startsWith("h")) {    // headings not shown in preview
                count += element.text().length();
                if (count > maxLength)
                    break;
            }
        }
        return builder.toString();
    }
}
