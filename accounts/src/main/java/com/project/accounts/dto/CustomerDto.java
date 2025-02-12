package com.project.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30 characters")
    private String name;

    @NotEmpty
    @Email(message = "Email address should be a valid value")
    private String email;


    private String mobileNumber;

    private AccountsDto accountDto;
}
