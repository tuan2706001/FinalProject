package com.java.project3.enums;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum UserType {
    ROLE_UNIVERSITY_QUAN_TRI((short) 1),
    ROLE_ENTERPRISE_QUAN_TRI((short) 2),
    ROLE_PERSONAL((short) 3),
    ROLE_ADMIN((short) 4),
    ROLE_PUBLIC((short) 5),
    ROLE_UNIVERSITY_QUAN_LY((short) 6),
    ROLE_UNIVERSITY_GIAO_VU((short) 7),
    ROLE_UNIVERSITY_SINH_VIEN((short) 8),
    ROLE_UNIVERSITY_GIANG_VIEN((short) 9),
    ROLE_ENTERPRISE_QUAN_LY((short) 10),
    ROLE_ENTERPRISE_QUAN_LY_THUC_TAP((short) 11);
    //    -------

    public Short value;

    UserType(Short value) {
        this.value = value;
    }

    public Short getValue() {
        return value;
    }

    public static List<UserType> userTypeListEnumWithout2Type(){
        List<UserType> list = new ArrayList<>();
        for (UserType v : values()){
            if(v.getValue().equals(ROLE_ADMIN.getValue()) || v.getValue().equals(ROLE_PUBLIC.getValue())){
                continue;
            }
            list.add(v);
        }
        return list;
    }

    public static List<Short> userTypeListShortWithout2Type(){
        List<Short> list = new ArrayList<>();
        for (UserType v : values()){
            if(v.getValue().equals(ROLE_ADMIN.getValue()) || v.getValue().equals(ROLE_PUBLIC.getValue())){
                continue;
            }
            list.add(v.getValue());
        }
        return list;
    }

    public static List<UserType> listUserTypeEnumForSignUp(){
        return Arrays.asList(ROLE_UNIVERSITY_QUAN_TRI, ROLE_ENTERPRISE_QUAN_TRI, ROLE_PERSONAL);
    }

    public static List<Short> listUserTypeShortForSignUp(){
        return Arrays.asList(ROLE_UNIVERSITY_QUAN_TRI.getValue(), ROLE_ENTERPRISE_QUAN_TRI.getValue(), ROLE_PERSONAL.getValue());
    }

    public static UserType findByRoleNameString(String role){
        if(role == null){
            return null;
        }
        for (UserType userType : values()){
            if(userType.name() == role){
                return userType;
            }
        }
        return null;
    }
}
