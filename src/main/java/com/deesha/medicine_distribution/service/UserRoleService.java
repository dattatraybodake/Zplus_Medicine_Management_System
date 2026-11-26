package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.model.UserRoles;

import java.util.List;

public interface UserRoleService {

    boolean saveRoles(UserRoles userRoles);

    List<UserRoles> viewAllRoles();

    UserRoles findRoleById(Integer roleId);

    UserRoles updateRole(Integer roleId, UserRoles userRoles);

    void deleteRole(Integer roleId);

}
