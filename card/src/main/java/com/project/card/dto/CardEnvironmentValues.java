package com.project.card.dto;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "accounts")
@Data
public class CardEnvironmentValues {

    private String message;
    private List<String> onCallSupport;

    private Map<String,String> contactDetails;
}
