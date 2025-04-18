package com.scaler.userservicecapstone.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserSignupRequestDto {
    private String email;
    private String password;
    private List<String> roles = new ArrayList<>();
}
