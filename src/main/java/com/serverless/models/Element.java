package com.serverless.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Element {
    @JsonProperty("id")
    public int id;
    @JsonProperty("nodes")
    public List<Integer> nodes;
    public List<Node> construcedNodes;
    public String value;
    public List<Element> neighbours;
}
