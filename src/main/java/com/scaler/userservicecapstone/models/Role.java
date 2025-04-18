package com.scaler.userservicecapstone.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Role extends BaseModel {

    private String name;

    public Role(String name) {
        this.name = name;
    }


}
