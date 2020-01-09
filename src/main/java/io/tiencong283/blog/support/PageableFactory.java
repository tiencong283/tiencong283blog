package io.tiencong283.blog.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableFactory {
    private final int pageSizeForLists;
    private final String sortingField = "publishDate";

    @Autowired
    public PageableFactory(@Value("${pageSizeForLists}") int pageSizeForLists) {
        this.pageSizeForLists = pageSizeForLists;
    }

    public Pageable all() {
        return build(0, Integer.MAX_VALUE);
    }

    public Pageable forLists(int page) {
        return build(page - 1, pageSizeForLists);
    }

    private Pageable build(int page, int pageSize) {
        return PageRequest.of(page, pageSize, Sort.Direction.DESC, sortingField);
    }
}