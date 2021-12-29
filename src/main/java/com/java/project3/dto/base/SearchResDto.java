package com.java.project3.dto.base;

import lombok.Data;

@Data
public class SearchResDto {
	private Object data;
	private int totalPages;
	private int pageIndex;
	private long totalRecords;
}
