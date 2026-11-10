package com.deesha.medicine_distribution.serviceImpl;

import com.deesha.medicine_distribution.dto.LoginRequest;
import com.deesha.medicine_distribution.dto.LoginResponse;
import com.deesha.medicine_distribution.model.UserModel;
import com.deesha.medicine_distribution.repository.UserRepository;
import com.deesha.medicine_distribution.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean saveUsers(UserModel usermodel) {
        return userRepository.save(usermodel) != null;
    }

    @Override
    public List<UserModel> viewAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        UserModel usermodel = userRepository.findByEmail(loginRequest.getEmail());

        if (usermodel == null) {
            loginResponse.setFlag(false);
            loginResponse.setMessage("User not found");
            return loginResponse;
        }

        if (!Boolean.TRUE.equals(usermodel.getActive())) {
            loginResponse.setFlag(false);
            loginResponse.setMessage("User account is inactive");
            return loginResponse;
        }

        if (!Objects.equals(usermodel.getPassword(), loginRequest.getPassword())) {

            loginResponse.setFlag(false);
            loginResponse.setMessage("Password is not correct");
            return loginResponse;
        }

        //update last login time.
        usermodel.setLastLogin(LocalDateTime.now());
        userRepository.save(usermodel);

        loginResponse.setFlag(true);
        loginResponse.setMessage("Login Successful");

        return loginResponse;
    }

    //Using Put Mapping
    @Override
    public Boolean updateUserMaster(UserModel userModel) {
        try {
            if (userModel == null || userModel.getUserId() == null) {
                return false;
            }

            UserModel existingUser = userRepository
                    .findById(userModel.getUserId())
                    .orElse(null);

            if (existingUser == null) {
                return false;
            }

            BeanUtils.copyProperties(
                    userModel,
                    existingUser,
                    "userId"
            );
            userRepository.save(existingUser);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    @Override
//    public UserModel patchUser(Integer id, UserModel userModel) {
//        UserModel existing = userRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
//
//        // Update only the fields that were actually sent (non-null)
//        if (userModel.getFullName() != null)      existing.setFullName(userModel.getFullName());
//        if (userModel.getEmail() != null)         existing.setEmail(userModel.getEmail());
//        if (userModel.getContact() != null) existing.setContact(userModel.getContact());
//        if (userModel.getIsActive() != null)      existing.setIsActive(userModel.getIsActive());
//        if (userModel.getUserName() != null)      existing.setUserName(userModel.getUserName());
//        return userRepository.save(existing);
//    }

    @Override
    public void deleteUser(Integer id) {
        UserModel user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        userRepository.delete(user);
    }
}