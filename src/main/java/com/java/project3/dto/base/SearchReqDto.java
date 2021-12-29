package com.java.project3.dto.base;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.java.project3.constant.Constants.DEFAULT_PAGE_INDEX;
import static com.java.project3.constant.Constants.DEFAULT_PAGE_SIZE;


@Data
public class SearchReqDto {
	private int pageIndex = DEFAULT_PAGE_INDEX;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private String query;
	private List<String> sorts = new ArrayList<String>();
}