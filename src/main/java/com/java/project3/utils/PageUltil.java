package com.java.project3.utils;


import com.java.project3.dto.base.Page;

public class PageUltil {
    public static Page setDefault(Integer currentPage, Integer pageSize) {
        if (pageSize == null || pageSize == 0) {
            pageSize = 5;
        }
        if (currentPage == null || currentPage == 0) {
            currentPage = 1;
        }
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        return page;
    }

    public static Page format(Integer currentPage, Integer totalPage, Integer pageSize) {
        if (pageSize == null || pageSize == 0) {
            pageSize = 5;
        }
        if (currentPage == null || currentPage == 0) {
            currentPage = 1;
        }
        Page page = new Page();
        page.setTotalPage(totalPage);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        if (currentPage == null || currentPage <= 1) {
            if (totalPage <= 3) {
                page.setStartPage(1);
                page.setEndPage(totalPage);

                if (totalPage <= 1){
                    page.setShowPreviousPage(false);
                    page.setShowNextPage(false);
                    page.setShowFirstPage(false);
                    page.setShowEndPage(false);
                }
                else if (currentPage == 1){
                    page.setShowPreviousPage(false);
                    page.setShowNextPage(true);
                    page.setShowFirstPage(false);
                    page.setShowEndPage(true);
                }
                else {
                    page.setShowPreviousPage(true);
                    page.setShowNextPage(true);
                    page.setShowFirstPage(true);
                    page.setShowEndPage(true);
                }
            } else {
                page.setShowPreviousPage(false);
                page.setShowNextPage(true);
                page.setShowFirstPage(false);
                page.setShowEndPage(true);
                page.setStartPage(1);
                page.setEndPage(3);
            }
        } else {
            if (totalPage <= 3) {
                page.setStartPage(1);
                page.setEndPage(totalPage);

                if (currentPage == totalPage){
                    page.setShowPreviousPage(true);
                    page.setShowNextPage(false);
                    page.setShowFirstPage(true);
                    page.setShowEndPage(false);
                }
                else {
                    page.setShowPreviousPage(true);
                    page.setShowNextPage(true);
                    page.setShowFirstPage(true);
                    page.setShowEndPage(true);
                }
            } else {
                if (currentPage > totalPage - 1) {
                    page.setShowPreviousPage(true);
                    page.setShowNextPage(false);
                    page.setShowFirstPage(true);
                    page.setShowEndPage(false);
                    page.setStartPage(totalPage - 2);
                    page.setEndPage(totalPage);
                } else {
                    page.setShowPreviousPage(true);
                    page.setShowNextPage(true);
                    page.setShowFirstPage(true);
                    page.setShowEndPage(true);
                    page.setStartPage(currentPage - 1);
                    page.setEndPage(currentPage + 1);
                }
            }
        }
        return page;
    }
}
