//package com.deesha.medicine_distribution.dto;
//
//import com.deesha.medicine_distribution.model.UserModel;
//
//import java.time.LocalDateTime;
//
//public final class UserDto {
//
//    public record UserRequest(
//            Integer roleId,
//            String fullName,
//            String email,
//            String contactNumber,
//            String userName,
//            String password,
//            Boolean isActive,
//            String actionBy          // who is creating/updating/deleting
//    ) {}
//
//    public record UserResponse(
//            Integer userId, Integer roleId, String fullName, String email,
//            String contactNumber, String userName, Boolean isActive,
//            LocalDateTime lastLogin, String createdBy, LocalDateTime created,
//            String modifiedBy, LocalDateTime modified) {
//
//        public static UserResponse from(UserModel u) {
//            return new UserResponse(
//                    u.getUserId(),
//                    u.getRole() != null ? u.getRole().getRoleId() : null, // adjust getter name to your UserRoles
//                    u.getFullName(), u.getEmail(), u.getContactNumber(), u.getUserName(),
//                    u.getActive(), u.getCreatedBy(), u.getCreated(),
//                    u.getModifiedBy(), u.getModified());
//        }
//    }
//
//    public record LoginRequest(String userName, String password) {}
//
//    public record LoginResponse(Integer userId, String fullName, Integer roleId, LocalDateTime lastLogin) {}
//}
