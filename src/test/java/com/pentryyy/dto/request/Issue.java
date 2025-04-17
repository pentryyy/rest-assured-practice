package com.pentryyy.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    private String     summary;
    private String     description;
    private ProjectRef project;

    @JsonIgnore
    private String createdIssueId;
}
