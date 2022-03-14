package com.serverless.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Input {
    @JsonProperty("nodes")
    public List<Node> nodes;
    @JsonProperty("elements")
    public List<Element> elements;
    @JsonProperty("values")
    public List<Value> values;
    @JsonProperty("N")
    public int N;
}