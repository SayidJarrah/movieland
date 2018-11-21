package com.dkorniichuk.movieland.dao.util;

import java.util.ArrayList;
import java.util.List;

public class Page<E> {
    private int pageNumber;
    private int pageAvailable;
    private int totalItems;
    private List<E> pageItems = new ArrayList<>();

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageAvailable() {
        return pageAvailable;
    }

    public void setPageAvailable(int pageAvailable) {
        this.pageAvailable = pageAvailable;
    }

    public List<E> getPageItems() {
        return pageItems;
    }

    public void setPageItems(List<E> pageItems) {
        this.pageItems = pageItems;
    }
}
