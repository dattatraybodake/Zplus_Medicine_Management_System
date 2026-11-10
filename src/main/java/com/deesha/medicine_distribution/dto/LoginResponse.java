package com.deesha.medicine_distribution.dto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import java.time.LocalDateTime;
import lombok.Data;


@Data
public class LoginResponse {
    String message;
    Boolean flag;
}