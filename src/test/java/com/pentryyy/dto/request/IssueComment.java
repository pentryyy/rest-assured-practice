package com.pentryyy.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IssueComment {
    private String text;
}
