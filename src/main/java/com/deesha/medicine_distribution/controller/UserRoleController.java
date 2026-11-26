package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.model.UserRoles;
import com.deesha.medicine_distribution.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/save")
    public ResponseEntity<Boolean> registerRole(@RequestBody UserRoles userRoles) {
        boolean saved = userRoleService.saveRoles(userRoles);
        if (saved) {
            return new ResponseEntity<>(true, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/viewRoles")
    public ResponseEntity<List<UserRoles>> getAllRoles() {
        List<UserRoles> roles = userRoleService.viewAllRoles();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/findRole/{roleId}")
    public ResponseEntity<UserRoles> findRoleById(@PathVariable Integer roleId) {
        try {
            UserRoles role = userRoleService.findRoleById(roleId);
            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateRole/{roleId}")
    public ResponseEntity<UserRoles> updateRole(@PathVariable Integer roleId,
                                                @RequestBody UserRoles userRoles) {
        try {
            UserRoles updated = userRoleService.updateRole(roleId, userRoles);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteRole/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable Integer roleId) {
        try {
            userRoleService.deleteRole(roleId);
            return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}