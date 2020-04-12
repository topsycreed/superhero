package com.topsycreed.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.processing.Generated;

@Generated("http://www.jsonschema2pojo.org/")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "birthDate",
        "city",
        "fullName",
        "gender",
        "id",
        "mainSkill",
        "phone"
})
public class SuperheroModel {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("fullName")
    private String fullName;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("city")
    private String city;
    @JsonProperty("mainSkill")
    private String mainSkill;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("phone")
    private String phone;

    /**
     * No args constructor for use in serialization
     */
    public SuperheroModel() {
    }

    /**
     * @param mainSkill
     * @param gender
     * @param city
     * @param phone
     * @param fullName
     * @param id
     * @param birthDate
     */
    public SuperheroModel(Integer id, String fullName, String birthDate, String city, String mainSkill, String gender, String phone) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.city = city;
        this.mainSkill = mainSkill;
        this.gender = gender;
        this.phone = phone;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        return fullName;
    }

    @JsonProperty("fullName")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JsonProperty("birthDate")
    public String getBirthDate() {
        return birthDate;
    }

    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("mainSkill")
    public String getMainSkill() {
        return mainSkill;
    }

    @JsonProperty("mainSkill")
    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }

    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("phone")
    public Object getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("fullName", fullName)
                .append("birthDate", birthDate)
                .append("city", city)
                .append("mainSkill", mainSkill)
                .append("gender", gender)
                .append("phone", phone)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(mainSkill)
                .append(gender)
                .append(city)
                .append(phone)
                .append(fullName)
                .append(id)
                .append(birthDate)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SuperheroModel) == false) {
            return false;
        }
        SuperheroModel rhs = ((SuperheroModel) other);
        return new EqualsBuilder()
                .append(mainSkill, rhs.mainSkill)
                .append(gender, rhs.gender)
                .append(city, rhs.city)
                .append(phone, rhs.phone)
                .append(fullName, rhs.fullName)
                .append(id, rhs.id)
                .append(birthDate, rhs.birthDate)
                .isEquals();
    }

    public static enum Gender {
        M, F
    }
}