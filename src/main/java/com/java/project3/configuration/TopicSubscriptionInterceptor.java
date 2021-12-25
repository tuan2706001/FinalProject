//package com.java.project3.configuration;
//
//import lombok.SneakyThrows;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.security.authentication.RememberMeAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//
//import java.security.Principal;
//import java.util.LinkedHashMap;
//import java.util.List;
//
//public class TopicSubscriptionInterceptor implements ChannelInterceptor {
//    @Autowired
//    MessengerService messengerService;
//
//    @SneakyThrows
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel){
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        String destination = accessor.getDestination();
//
//        String[] parts = destination.split("/");
//        Long conversationId = null;
//        try {
//            conversationId = Long.parseLong(parts[3]);
//        }catch (Exception e){
//            throw new Exception("Invalid conversation id!");
//        }
//
//        if(!parts[3].equals("3576339764297728")){
//            throw new Exception("Exception message");
//        }
//        Principal principal = accessor.getUser();
//        Object principal1 = null;
//        if(principal instanceof UsernamePasswordAuthenticationToken){
//            principal1 = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
//        }else if(principal instanceof RememberMeAuthenticationToken){
//            principal1 = ((RememberMeAuthenticationToken) principal).getPrincipal();
//        }
//
//        User user = null;
//        if (principal1 instanceof User) {
//            user = (User) principal1;
//        } else if (principal1 instanceof RequestContext) {
//            RequestContext requestContext = (RequestContext) principal1;
//            user = requestContext.getUser();
//        }
//        if(user != null){
//            if(conversationId != null){
//                List<LinkedHashMap<String, Object>> conversationHandleDTOList = (List<LinkedHashMap<String, Object>>) messengerService.getConversationByUserId(user.getId()).getObject();
//                Long finalConversationId1 = conversationId;
//                Boolean check = conversationHandleDTOList.stream().anyMatch(u -> u.get("id").equals(finalConversationId1));
//                if(check == true){
//                    return message;
//                }else{
//                    throw new Exception("Invalid conversation");
//                }
//            }
//
//        }else{
//            throw new Exception("Invalid user");
//        }
//
//        return null;
//    }
//}
