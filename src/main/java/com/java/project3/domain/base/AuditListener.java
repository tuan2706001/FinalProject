//package com.java.project3.domain.base;
//
//import com.java.project3.domain.user.User;
//import com.java.project3.service.base.ContextService;
//import com.java.project3.service.base.GenIdService;
//import com.java.project3.utils.BeanUtil;
//import com.java.project3.utils.BeanUtil;
//import org.apache.tomcat.util.descriptor.web.ContextService;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.*;
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//
//@Service
//public class AuditListener {
//
//    @PreUpdate
//    @PreRemove
//    @PrePersist
//
//    public void setAudit(AuditAuto auditAuto) {
//        ContextService contextService = BeanUtil.getBean(ContextService.class);
//        User user = contextService.getCurrentUser();
//        if (auditAuto.getId() == null || auditAuto.getId() == 0) {
//            if (user != null) {
//                auditAuto.setCreatedBy(user.getId());
//                auditAuto.setUpdatedBy(user.getId());
//                auditAuto.setCreatedByName(user.getFullName());
//                auditAuto.setUpdatedByName(user.getFullName());
//            }
//            LocalDateTime now = LocalDateTime.now();
//            GenIdService genIdService = BeanUtil.getBean(GenIdService.class);
//            auditAuto.setId(genIdService.nextId());
//            auditAuto.setCreatedAt(now);
//            auditAuto.setUpdatedAt(now);
//            auditAuto.setIsDeleted(false);
//        } else {
//            if (user != null) {
//                auditAuto.setUpdatedBy(contextService.getCurrentUser().getId());
//                auditAuto.setUpdatedByName(contextService.getCurrentUser().getFullName());
//            }
//            auditAuto.setUpdatedAt(LocalDateTime.now());
//
//
//        }
//
//
//    }
////
////    @PostPersist
////    @PostUpdate
////    @Transactional(propagation = Propagation.REQUIRES_NEW)
////    public void InsertFTS(Object object) throws IllegalAccessException, IOException {
////        ContextService contextService = BeanUtil.getBean(ContextService.class);
////        FullTextSearchUtil fullTextSearchUtil = BeanUtil.getBean(FullTextSearchUtil.class);
////        if (fullTextSearchUtil.checkFTSAble(object)){
////            FullTextSearchService fullTextSearchService = BeanUtil.getBean(FullTextSearchService.class);
////            FullTextSearch fullTextSearch = fullTextSearchUtil.getFTS(object);
////            if((contextService.getCurrentOrg() == null ? null : contextService.getCurrentOrg().getId()) == null){
////                if(object instanceof Call){
////                    Call ftsCall = (Call) object;
////                    if(!ftsCall.getStatus().equals(NHAP.value)){
////                        fullTextSearchService.create(fullTextSearch, contextService.getCurrentUser().getId());
////                    }
////                }
////            }else{
////                if(object instanceof Call){
////                    Call ftsCall = (Call) object;
////                    if(!ftsCall.getStatus().equals(NHAP.value)){
////                        fullTextSearchService.create(fullTextSearch, (contextService.getCurrentOrg() == null ? null : contextService.getCurrentOrg().getId()));
////                    }
////                }
////            }
////        }
////    }
////
////    @PostRemove
////    @Transactional(propagation = Propagation.REQUIRES_NEW)
////    public void DeleteFTS(Object object) throws IllegalAccessException {
////        FullTextSearchUtil fullTextSearchUtil = BeanUtil.getBean(FullTextSearchUtil.class);
////        if (fullTextSearchUtil.checkFTSAble(object)){
////            FullTextSearchService fullTextSearchService = BeanUtil.getBean(FullTextSearchService.class);
////            FullTextSearch fullTextSearch = fullTextSearchUtil.getFTS(object);
////            if(object instanceof Call){
////                Call ftsCall = (Call) object;
////                if(!ftsCall.getStatus().equals(NHAP.value)){
////                    fullTextSearchService.delete(fullTextSearch);
////                }
////            }
////        }
////    }
////
////}
