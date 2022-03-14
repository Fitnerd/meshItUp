package com.serverless.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Node {
    @JsonProperty("id")
    public int id;
    @JsonProperty("x")
    public float x;
    @JsonProperty("y")
    public float y;
}
