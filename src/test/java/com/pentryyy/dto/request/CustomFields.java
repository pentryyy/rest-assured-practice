package com.pentryyy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomFields {
    private String name;
    private Value value;

    @JsonProperty("$type")
    private String type;
}
