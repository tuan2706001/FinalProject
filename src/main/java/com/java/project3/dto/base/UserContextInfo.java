package com.java.project3.dto.base;

import liquibase.hub.model.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import javax.management.relation.Role;
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
