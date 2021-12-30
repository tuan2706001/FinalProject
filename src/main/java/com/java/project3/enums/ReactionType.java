//package com.java.project3.enums;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public enum ReactionType {
//    FOLLOW((short) 1),
//
//    LIKE((short) 2),
//    LOVE((short) 3),
//    SMILE((short) 4),
//    SAD((short) 5),
//
//    CARE((short) 6),
//
//    VOTE1((short) 7),
//    VOTE2((short) 8),
//    VOTE3((short) 9),
//    VOTE4((short) 10),
//    VOTE5((short) 11);
//    //    -------
//
//    public Short value;
//
//    ReactionType(Short value) {
//        this.value = value;
//    }
//
//    public Short getValue() {
//        return value;
//    }
//
//    public static List<ReactionType> llss(){
//        return Arrays.asList(LIKE,LOVE,SMILE,SAD);
//    }
//
//    public static List<Short> llssShort(){
//        return Arrays.asList(LIKE.getValue(),LOVE.getValue(),SMILE.getValue(),SAD.getValue());
//    }
//
//    public static boolean checkOfLLSS(ReactionType input){
//        if(llss().contains(input)){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//
//    public static List<ReactionType> votes(){
//        return Arrays.asList(VOTE1,VOTE2,VOTE3,VOTE4,VOTE5);
//    }
//
//    public static List<Short> listVoteShort(){
//        return Arrays.asList(VOTE1.getValue(),VOTE2.getValue(),VOTE3.getValue(),VOTE4.getValue(),VOTE5.getValue());
//    }
//
//    public static boolean checkOfVotes(ReactionType input){
//        if(votes().contains(input)){
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//
//
//    public static List<ReactionType> listReactionType(){
//        List<ReactionType> reactionTypes = new ArrayList<>();
//        for (ReactionType v : values()) {
//            reactionTypes.add(v);
//        }
//        return reactionTypes;
//    }
//
//    public static List<Short> listReactionTypeShort(){
//        return listReactionType().stream().map(p->{return p.getValue();}).collect(Collectors.toList());
//    }
//
//    public static ReactionType findByShort(Short abbr) {
//        for (ReactionType v : values()) {
//            if (v.getValue().equals(abbr)) {
//                return v;
//            }
//        }
//        return null;
//    }
//}
