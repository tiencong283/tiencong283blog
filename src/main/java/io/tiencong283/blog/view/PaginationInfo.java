package io.tiencong283.blog.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PaginationInfo {
    private final long currentPage;
    private final long totalPage;

    public PaginationInfo(Page<?> page) {
        this.currentPage = page.getNumber() + 1;    // 1-indexed
        this.totalPage = page.getTotalPages();
    }

    public boolean isVisible() {
        return isPreviousVisible() || isNextVisible();
    }

    public boolean isPreviousVisible() {
        return currentPage > 1;
    }

    public boolean isNextVisible() {
        return currentPage < totalPage;
    }

    public long getNextPageNumber() {
        return currentPage + 1;
    }

    public long getPreviousPageNumber() {
        if (isPreviousVisible()) {
            return currentPage - 1;
        }
        return currentPage;
    }
}
