package com.java.project3.dto.base;

import com.bkh.vnoip.domain.user.Organization;
import com.bkh.vnoip.domain.user.Role;
import com.bkh.vnoip.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserContextInfo {
    private String quanTriPath;
    private User currentUser;
    private String websitePath;
    private List<Organization> organizationList;
    private Organization currentOrganization;
    private List<Role> listRoleOrg;
}
