package com.pentryyy.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectItem {
    private String id;

    @JsonProperty("$type")
    private String type;
}