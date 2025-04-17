package com.pentryyy.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateIssue {
    private String             summary;
    private String             description;
    private ProjectRef         project;
    private List<CustomFields> customFields;
    
    @JsonProperty("$type")
    private String type;
}
