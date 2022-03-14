package com.serverless.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Value {
    @JsonProperty("element_id")
    public int element_id;
    @JsonProperty("value")
    public String value;
}
