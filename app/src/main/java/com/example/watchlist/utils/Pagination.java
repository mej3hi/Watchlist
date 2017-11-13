package com.example.watchlist.utils;

/**
 * Handles all the pagination.
 *
 */

public class Pagination {

    private boolean isLastPage;
    private boolean isLoading;
    private int totalPages;
    private int currentPage;

    public Pagination() {
        this.isLastPage = false;
        this.isLoading = false;
        this.totalPages = 0;
        this.currentPage = 1;
    }

    public boolean isLastPage() {
        return this.isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.isLastPage = lastPage;
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
