package com.deesha.medicine_distribution.controller;

import com.deesha.medicine_distribution.model.UserRoles;
import com.deesha.medicine_distribution.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("roles")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/save")
    public ResponseEntity registerRole(@RequestBody UserRoles userRoles)
    {
        boolean saved = userRoleService.saveRoles(userRoles);
        if(saved)
        {
            return new ResponseEntity(saved, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity(saved, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("viewRoles")
    public List<UserRoles> getAllRoles() throws Exception {
        List<UserRoles> roles = userRoleService.viewAllRoles();
        if (roles.size() != 0) {
            System.out.println("list = " + roles.size());
            return userRoleService.viewAllRoles();
        } else {
            throw new RuntimeException("list is null");
        }
    }
}
