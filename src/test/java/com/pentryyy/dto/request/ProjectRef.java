package com.pentryyy.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectRef {
    private String id;

    @JsonProperty("$type")
    private String type;
}
