package com.pentryyy.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Project {
    private String  id;
    private String  shortName;
    private String  name;
    private User    leader;
    private String  description;
    private Boolean archived;
}
