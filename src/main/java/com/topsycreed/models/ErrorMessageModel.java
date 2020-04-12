package com.topsycreed.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessageModel {

    @JsonProperty("status")
    private Integer status;
    @JsonProperty("error")
    private String error;

    /**
     * @param status
     * @param error
     */
    public ErrorMessageModel(Integer status, String error) {
        super();
        this.status = status;
        this.error = error;
    }

    /**
     * No args constructor for use in serialization
     */
    public ErrorMessageModel() {
    }

    @JsonProperty("status")
    public Integer getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Integer status) {
        this.status = status;
    }

    @JsonProperty("error")
    public String getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("error", error)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(status)
                .append(error)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ErrorMessageModel) == false) {
            return false;
        }
        ErrorMessageModel rhs = ((ErrorMessageModel) other);
        return new EqualsBuilder()
                .append(status, rhs.status)
                .append(error, rhs.error)
                .isEquals();
    }
}
