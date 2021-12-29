package com.java.project3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Page {
    private boolean showFirstPage;
    private boolean showEndPage;
    private boolean showPreviousPage;
    private boolean showNextPage;

    private Integer totalCount;
    private Integer currentPage;
    private Integer totalPage;
    private Integer pageSize;

    private Integer startPage;
    private Integer endPage;
}
