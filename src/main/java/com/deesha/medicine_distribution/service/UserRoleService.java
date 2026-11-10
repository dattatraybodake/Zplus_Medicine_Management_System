package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.model.UserRoles;

import java.util.List;

public interface UserRoleService {

    boolean saveRoles(UserRoles userroles);
    List<UserRoles> viewAllRoles();
    public void deleteRole(Integer roleId);
}
