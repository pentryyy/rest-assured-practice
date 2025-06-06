package com.pentryyy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String id;

    @JsonProperty("$type")
    private String type;
}
