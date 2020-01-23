package io.tiencong283.blog.view;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class PaginationInfo {
    private final long currentPage; // 1-indexed
    private final long totalPage;
    private final String rootPath;
    private String param = "page";

    public PaginationInfo(Page<?> page, String rootPath) {
        this.currentPage = page.getNumber() + 1;
        this.totalPage = page.getTotalPages();
        this.rootPath = rootPath;
    }

    public PaginationInfo(Page<?> page, String rootPath, String param) {
        this(page, rootPath);
        this.param = param;
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
        if (currentPage < totalPage) {
            return currentPage + 1;
        }
        return totalPage;
    }

    public String getNextPagePath() {
        return String.format("%s?%s=%d", rootPath, param, getNextPageNumber());
    }

    public long getPreviousPageNumber() {
        if (isPreviousVisible()) {
            return currentPage - 1;
        }
        return currentPage;
    }

    public String getPreviousPagePath() {
        if (getPreviousPageNumber() == 1)
            return rootPath;
        return String.format("%s?%s=%d", rootPath, param, getPreviousPageNumber());
    }
}
