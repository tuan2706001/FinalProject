//package com.java.project3.service.base;
//
//import com.java.project3.domain.Ministry;
//import com.java.project3.dto.base.RequestContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.RememberMeAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.HttpServletRequest;
//
//@Service
//public class ContextService {
//    @Autowired
//    HttpServletRequest request;
//
////    public Organization getCurrentOrg(){
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        if (authentication instanceof UsernamePasswordAuthenticationToken) {
////            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
////            HashMap<String, Object> detail = (HashMap<String, Object>) auth.getDetails();
////            Long orgId = null;
////            try {
////                orgId = Long.parseLong(detail.get("currentOrgId").toString());
////            }
////            catch (Exception e){
////            }
////            Long finalOrgId = orgId;
////            return getCurrentUser().getAllOrg().stream().filter(p->p.getId().equals(finalOrgId)).findFirst().orElse(null);
////        }
////        return null;
////    }
//
//
//    public Ministry getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof UsernamePasswordAuthenticationToken) {
//            if (authentication.isAuthenticated()) {
//                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                Object principal = auth.getPrincipal();
//                if (principal instanceof Ministry) {
//                    return (Ministry) principal;
//                } else if (principal instanceof RequestContext) {
//                    RequestContext requestContext = (RequestContext) auth.getPrincipal();
//                    return requestContext.getMinistry();
//                } else {
//                    return null;
//                }
//            }
//        } else if (authentication instanceof RememberMeAuthenticationToken) {
//            if (authentication.isAuthenticated()) {
//                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                Object principal = auth.getPrincipal();
//                if (principal instanceof Ministry) {
//                    return (Ministry) principal;
//                } else if (principal instanceof RequestContext) {
//                    RequestContext requestContext = (RequestContext) auth.getPrincipal();
//                    return requestContext.getMinistry();
//                } else {
//                    return null;
//                }
//            }
//        } else if (authentication instanceof AnonymousAuthenticationToken) {
//            AnonymousAuthenticationToken anonymousAuthenticationToken = (AnonymousAuthenticationToken) authentication;
//            return null;
//        }
//        return null;
//    }
//
////    public boolean checkCurrentUserByOrg(Long orgId) {
////        orgId = orgId == null ? -1 : orgId;
////        User user = getCurrentUser();
////        if(user == null){
////            return false;
////        }
////        else{
////            Long finalOrgId = orgId;
////            var check = user.getAllOrgForCurrentUser().stream().filter(p->p.getId().equals(finalOrgId)).findAny();
////            if(check.isPresent()){
////                return true;
////            }
////            else{
////                return false;
////            }
////        }
////    }
//
////    public Boolean currentIsAdmin(){
////        User user = getCurrentUser();
////        if(user == null){
////            return false;
////        }
////        else{
////            if(!hasROLE_ADMIN()){
////                return false;
////            }
////        }
////        return true;
////    }
//
////    public ParentIdAndRoleDto getParentIdAndRoleName() {
////        ParentIdAndRoleDto parentIdAndRoleDto = new ParentIdAndRoleDto();
////        User user = getCurrentUser();
////        if (user != null) {
////            var checkRole = getRoleName().getParent();
////            if (checkRole == ROLE_NAME.ROLE_UNIVERSITY) {
////                parentIdAndRoleDto.setParentRoleName(ROLE_NAME.ROLE_UNIVERSITY);
////            } else if (checkRole == ROLE_NAME.ROLE_ENTERPRISE) {
////                parentIdAndRoleDto.setParentRoleName(ROLE_NAME.ROLE_ENTERPRISE);
////            } else {
////                parentIdAndRoleDto.setParentRoleName(getRoleName());
////                parentIdAndRoleDto.setParentId(user.getId());
////            }
////
////            if (parentIdAndRoleDto.getParentId() == null) {
////                if (user.getOrganization().isPresent()) {
////                    Long orgId = user.getOrganization().get().getId();
////                    parentIdAndRoleDto.setParentId(orgId);
////                }
////            }
////        }
////        return parentIdAndRoleDto;
////    }
//
////    public ROLE_NAME getRoleName() {
////        if (this.hasROLE_ADMIN()) {
////            return ROLE_NAME.ROLE_ADMIN;
////        } else if (this.hasROLE_ENTERPRISE_QUAN_TRI()) {
////            return ROLE_NAME.ROLE_ENTERPRISE_QUAN_TRI;
////        } else if (this.hasROLE_ENTERPRISE_QUAN_LY()) {
////            return ROLE_NAME.ROLE_ENTERPRISE_QUAN_LY;
////        } else if (this.hasROLE_ENTERPRISE_QUAN_LY_THUC_TAP()) {
////            return ROLE_NAME.ROLE_ENTERPRISE_QUAN_LY_THUC_TAP;
////        } else if (this.hasROLE_UNIVERSITY_QUAN_TRI()) {
////            return ROLE_NAME.ROLE_UNIVERSITY_QUAN_TRI;
////        } else if (this.hasROLE_UNIVERSITY_QUAN_LY()) {
////            return ROLE_NAME.ROLE_UNIVERSITY_QUAN_LY;
////        } else if (this.hasROLE_UNIVERSITY_SINH_VIEN()) {
////            return ROLE_NAME.ROLE_UNIVERSITY_SINH_VIEN;
////        } else if (this.hasROLE_UNIVERSITY_GIANG_VIEN()) {
////            return ROLE_NAME.ROLE_UNIVERSITY_GIANG_VIEN;
////        } else if (this.hasROLE_PERSONAL()) {
////            return ROLE_NAME.ROLE_PERSONAL;
////        } else {
////            return ROLE_NAME.ROLE_PUBLIC;
////        }
////    }
////
////
////    public boolean hasROLE_ADMIN() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_ADMIN.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_ENTERPRISE_QUAN_TRI() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_ENTERPRISE_QUAN_TRI.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_ENTERPRISE_QUAN_LY() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_ENTERPRISE_QUAN_LY.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_ENTERPRISE_QUAN_LY_THUC_TAP() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_ENTERPRISE_QUAN_LY_THUC_TAP.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_UNIVERSITY_QUAN_TRI() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_UNIVERSITY_QUAN_TRI.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_UNIVERSITY_QUAN_LY() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_UNIVERSITY_QUAN_LY.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////
////    }
////
////    public boolean hasROLE_UNIVERSITY_SINH_VIEN() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_UNIVERSITY_SINH_VIEN.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////
////    }
////
////    public boolean hasROLE_UNIVERSITY_GIANG_VIEN() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_UNIVERSITY_GIANG_VIEN.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////
////    }
////
////    public boolean hasROLE_PERSONAL() {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_PERSONAL.name())).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////
////    }
////
////    // region new
////    public List<ROLE_NAME> getAllRoleNameInOrg(Long orgId) {
////        User user = getCurrentUser();
////        List<ROLE_NAME> roleNames = new ArrayList<>();
////        List<Role> roles = user.getAllRole().stream().filter(p->p.getOrgId().equals(orgId)).collect(Collectors.toList());
////        for (var role : roles){
////            ROLE_NAME roleName = ROLE_NAME.findByRoleNameString(role.getName());
////            if(roleName != null){
////                roleNames.add(roleName);
////            }
////        }
////        return roleNames;
////    }
////
////    public boolean hasROLE_ENTERPRISE_QUAN_TRI_IN_ORG(Long orgId) {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_ENTERPRISE_QUAN_TRI.name()) && x.getOrgId().equals(orgId)).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_ENTERPRISE_QUAN_LY_IN_ORG(Long orgId) {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_ENTERPRISE_QUAN_LY.name()) && x.getOrgId().equals(orgId)).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_ENTERPRISE_QUAN_LY_THUC_TAP_IN_ORG(Long orgId) {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_ENTERPRISE_QUAN_LY_THUC_TAP.name()) && x.getOrgId().equals(orgId)).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_UNIVERSITY_QUAN_TRI_IN_ORG(Long orgId) {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_UNIVERSITY_QUAN_TRI.name()) && x.getOrgId().equals(orgId)).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_UNIVERSITY_QUAN_LY_IN_ORG(Long orgId) {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_UNIVERSITY_QUAN_LY.name()) && x.getOrgId().equals(orgId)).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_UNIVERSITY_SINH_VIEN_IN_ORG(Long orgId) {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_UNIVERSITY_SINH_VIEN.name()) && x.getOrgId().equals(orgId)).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
////
////    public boolean hasROLE_UNIVERSITY_GIANG_VIEN_IN_ORG(Long orgId) {
////        User user = this.getCurrentUser();
////        if (user != null) {
////            Optional<Role> role = user.getRoles().stream().filter(x -> x.getName().equals(ROLE_NAME.ROLE_UNIVERSITY_GIANG_VIEN.name()) && x.getOrgId().equals(orgId)).findAny();
////            if (role.isPresent()) {
////                return true;
////            }
////        }
////        return false;
////    }
//    // endregion
//}
