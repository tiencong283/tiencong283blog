package io.tiencong283.blog.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableFactory {
    public final int pageSizeForLists;
    public final int pageSizeForDashboard;

    public PageableFactory(@Value("${pageSizeForLists}") int pageSizeForLists, @Value("${pageSizeForDashboard}") int pageSizeForDashboard) {
        this.pageSizeForLists = pageSizeForLists;
        this.pageSizeForDashboard = pageSizeForDashboard;
    }

    public Pageable all() {
        return build(0, Integer.MAX_VALUE);
    }

    public Pageable forLists(int page) {
        return build(page - 1, pageSizeForLists);
    }

    public Pageable forDashboard(int page) {
        return build(page - 1, pageSizeForDashboard);
    }

    private Pageable build(int page, int pageSize) {
        return PageRequest.of(page, pageSize, Sort.Direction.DESC, "publishDate");
    }
}