package com.java.project3.constant;

public enum Enum {

    QUAN_LY((short) 1),
    GIAO_VU((short) 2),
    SINH_VIEN((short) 3);


    public Short value;

    Enum(Short value) {
        this.value = value;
    }

    public Short getValue() {
        return value;
    }

    public static Enum findByShort(Short abbr) {
        for (Enum v : values()) {
            if (v.getValue().equals(abbr)) {
                return v;
            }
        }
        return null;
    }

    public static String findNameByValue(Short abbr) {
        for (Enum v : values()) {
            if (v.getValue().equals(abbr)) {
                return v.name();
            }
        }
        return null;
    }
}
