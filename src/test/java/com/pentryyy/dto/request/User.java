package com.pentryyy.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class User {
    private String login;
    private String name;
    private String id;
    private String type;
}
