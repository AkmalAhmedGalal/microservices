package com.project.accounts.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {

    private String statusCode;

    private String statusMessage;
}
