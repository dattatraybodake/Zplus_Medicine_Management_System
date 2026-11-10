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
        return userRolesRepository.save(userRoles)!=null;
    }

    @Override
    public List<UserRoles> viewAllRoles() {
        return userRolesRepository.findAll();
    }

    @Override
    public void deleteRole(Integer roleId) {
        UserRoles roles = userRolesRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Roles is not found" + roleId));
        userRolesRepository.delete(roles);
    }
}
