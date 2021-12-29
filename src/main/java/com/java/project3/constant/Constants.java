package com.java.project3.constant;

import java.util.regex.Pattern;

public interface Constants {
	Pattern SEARCH_QUERY_PATTERN = Pattern.compile("(OR-)?(N-|S-|D-|B-|LD-|LDT-)(\\w+?)(=L|==|=|<|>|<=|>=|#|!=)(\"([^\"]+)\")");
	String EMPTY = "";
	String DOUBLE_QUOTES = "\"";
	String COMMA = ",";
	String COLON = ":";
	String LIKE_PRE_POST = "%%%s%%";
	String EQUALS = "=";
	String EQUALS_LOWER = "=L";
	String L_T = "<";
	String E_T_S = "=="; //exactly the same
	String NOT_EQUALS = "!=";
	String G_T = ">";
	String L_T_EQUALS = "<=";
	String G_T_EQUALS = ">=";
	String IN = "#";
	String DEFAULT_PROP = "id";
	int DEFAULT_PAGE_SIZE = 30;
	int DEFAULT_PAGE_INDEX = 0;
}
