//package com.java.project3.enums;
//
//public enum CategoryType {
//    CALL((short) 1),
//    NEWS((short) 2),
//    EVENT((short) 3);
//    //    -------
//
//    public Short value;
//
//    CategoryType(Short value) {
//        this.value = value;
//    }
//
//    public Short getValue() {
//        return value;
//    }
//
//    public static CategoryType findByShort(Short abbr){
//        for(CategoryType v : values()){
//            if( v.getValue().equals(abbr)){
//                return v;
//            }
//        }
//        return null;
//    }
//}