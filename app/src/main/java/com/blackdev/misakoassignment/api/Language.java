package com.blackdev.misakoassignment.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Language {
    @JsonProperty("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
