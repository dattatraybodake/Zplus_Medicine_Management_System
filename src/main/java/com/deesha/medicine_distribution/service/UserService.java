package com.deesha.medicine_distribution.service;

import com.deesha.medicine_distribution.dto.LoginRequest;
import com.deesha.medicine_distribution.dto.LoginResponse;
import com.deesha.medicine_distribution.model.UserModel;

import java.util.List;

public interface UserService {
    boolean saveUsers(UserModel usermodel);
    List<UserModel> viewAllUsers();
    LoginResponse login(LoginRequest loginRequest);
    Boolean updateUserMaster(UserModel userModel);
//    UserModel patchUser(Integer id, UserModel userModel);
    public void deleteUser(Integer id);
}