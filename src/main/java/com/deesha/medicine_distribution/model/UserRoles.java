package com.deesha.medicine_distribution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter @Getter
@Table(name = "roles")
@ToString(exclude = "users")
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @Column(name = "discription")
    private String discription;

    @JsonIgnore
    @OneToMany(mappedBy = "role")
    private List<UserModel> users;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    public UserRoles() {
    }

    public UserRoles(Integer roleId, String roleName, List<UserModel> users, Boolean isActive) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.users = users;
        this.isActive = isActive;
    }
}