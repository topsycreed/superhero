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

    public SuperheroModel(SuperheroModel hero) {
        this.id = hero.getId();
        this.fullName = hero.getFullName();
        this.birthDate = hero.getBirthDate();
        this.city = hero.getCity();
        this.mainSkill = hero.getMainSkill();
        this.gender = hero.getGender();
        this.phone = hero.getPhone();
    }
}