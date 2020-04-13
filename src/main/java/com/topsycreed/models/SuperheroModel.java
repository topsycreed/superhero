package com.topsycreed.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperheroModel {

    private Integer id;
    private String fullName;
    private String birthDate;
    private String city;
    private String mainSkill;
    private String gender;
    private String phone;

    public enum Gender {
        M, F
    }
}