package com.scaler.userservicecapstone.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private Date expiryDate;
}
