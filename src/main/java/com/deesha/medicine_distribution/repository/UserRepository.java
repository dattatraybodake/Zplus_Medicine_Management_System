package com.deesha.medicine_distribution.repository;

import com.deesha.medicine_distribution.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    UserModel findByEmail(String email);

//    @Modifying
//    @Query("UPDATE UserModel u SET u.lastLoginAt = :time WHERE u.userId = :id")
//    void updateLastLogin(@Param("id") Integer id, @Param("time") LocalDateTime time);
//
//    @Modifying
//    @Query("UPDATE UserModel u SET u.active = :active WHERE u.userId = :id")
//    int updateActive(@Param("id") Integer id, @Param("active") boolean active);
}