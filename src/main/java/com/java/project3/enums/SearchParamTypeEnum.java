package com.java.project3.enums;

public enum SearchParamTypeEnum {
	NUM_PARAM("N-"), STRING_PARAM("S-"), DATE_TIME_PARAM("D-"), BOOLEAN_PARAM("B-"), LOCAL_DATE_PARAM("LD-"), LOCAL_DATE_TIME_PARAM("LDT-");

	private String type;

	SearchParamTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static SearchParamTypeEnum valueOfType(String label) {
		for (SearchParamTypeEnum e : values())
			if (e.type.equals(label))
				return e;
		return null;
	}
}
