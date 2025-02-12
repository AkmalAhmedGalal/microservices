package com.project.accounts.dto;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "account")
@Data
public class AccountEnvironmentValues {

    private String message;
    private List<String> onCallSupport;

    private Map<String,String> contactDetails;
}
