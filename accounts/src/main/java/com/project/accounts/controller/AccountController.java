package com.project.accounts.controller;


import com.project.accounts.constants.AccountConstants;
import com.project.accounts.dto.AccountEnvironmentValues;
import com.project.accounts.dto.CustomerDto;
import com.project.accounts.dto.ResponseDto;
import com.project.accounts.services.framework.IAccountsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api", produces = {MediaType.APPLICATION_JSON_VALUE}) //these means that all the methods in this controller will return JSON
@RequiredArgsConstructor
@Validated

public class AccountController {

    private final IAccountsService accountsService;

    @Value("${build.version}")
    private String buildVersion;

    private final AccountEnvironmentValues accountEnvironmentValues;

    @PostMapping("/create-account")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);
        return new ResponseEntity<>(ResponseDto.builder()
                .statusCode(AccountConstants.STATUS201)
                .statusMessage(AccountConstants.MESSAGE_201).build(),HttpStatus.CREATED);
    }


    @GetMapping("/get-account-details")
    public ResponseEntity<CustomerDto> getAccountDetails(@RequestParam String mobileNumber) {

        return new ResponseEntity<>(accountsService.getAccountDetailsByMobileNumber(mobileNumber), HttpStatus.OK);

    }

    @PostMapping("update-account-details")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {

        boolean isUpdated = accountsService.updateAccount(customerDto);

        return new ResponseEntity<>(isUpdated ? ResponseDto.builder().statusCode(AccountConstants.STATUS200).statusMessage(AccountConstants.MESSAGE_200).build() :
                                                ResponseDto.builder().statusCode(AccountConstants.STATUS_500).statusMessage(AccountConstants.MESSAGE_500).build(),HttpStatus.OK);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam  String mobileNumber) {

        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        return new ResponseEntity<>(isDeleted ? ResponseDto.builder().statusCode(AccountConstants.STATUS200).statusMessage(AccountConstants.MESSAGE_200).build() :
                                                ResponseDto.builder().statusCode(AccountConstants.STATUS_500).statusMessage(AccountConstants.MESSAGE_500).build(),HttpStatus.OK);
    }

    @GetMapping("/get-version")
    public String getVersion() {
        return buildVersion;
    }


    @GetMapping("/get-configuration-properties")
    public AccountEnvironmentValues getConfigurationProperties() {
        return accountEnvironmentValues;
    }

}
