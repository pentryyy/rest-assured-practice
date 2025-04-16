package com.pentryyy.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
    private String  id;
    private String  shortName;
    private String  name;
    private User    leader;
    private String  description;
    private Boolean archived;
    private Boolean template;
}
