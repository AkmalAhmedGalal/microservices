package com.project.accounts.services.framework;

import com.project.accounts.dto.CustomerDto;

public interface IAccountsService {


    /**
     * Creates a new account with the provided customer details.
     *
     * @param customerDto the data transfer object containing customer details
     */
    void createAccount(CustomerDto customerDto);

    CustomerDto getAccountDetailsByMobileNumber(String mobileNumber);


    boolean updateAccount(CustomerDto customer);

    boolean deleteAccount(String mobileNumber);
}
