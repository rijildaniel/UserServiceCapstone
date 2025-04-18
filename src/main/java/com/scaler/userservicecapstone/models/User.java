package com.scaler.userservicecapstone.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String email;

    private String name;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
}
