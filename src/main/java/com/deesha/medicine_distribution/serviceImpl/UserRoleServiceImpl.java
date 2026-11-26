package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.model.UserRoles;
import com.deesha.medicine_distribution.repository.UserRolesRepository;
import com.deesha.medicine_distribution.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public boolean saveRoles(UserRoles userRoles) {
        return userRolesRepository.save(userRoles) != null;
    }

    @Override
    public List<UserRoles> viewAllRoles() {
        return userRolesRepository.findAll();
    }

    @Override
    public UserRoles findRoleById(Integer roleId) {
        return userRolesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
    }

    @Override
    public UserRoles updateRole(Integer roleId, UserRoles userRoles) {
        UserRoles existingRole = userRolesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));

        existingRole.setRoleName(userRoles.getRoleName());
        existingRole.setDiscription(userRoles.getDiscription());
        if (userRoles.getIsActive() != null) {
            existingRole.setIsActive(userRoles.getIsActive());
        }

        return userRolesRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Integer roleId) {
        UserRoles role = userRolesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
        userRolesRepository.delete(role);
    }
}
