package com.project.accounts.services.impl;

import com.project.accounts.constants.AccountConstants;
import com.project.accounts.dto.AccountsDto;
import com.project.accounts.dto.CustomerDto;
import com.project.accounts.entities.Account;
import com.project.accounts.entities.Customer;
import com.project.accounts.exception.ResourceNotFoundException;
import com.project.accounts.mapper.AccountMapper;
import com.project.accounts.mapper.CustomerMapper;
import com.project.accounts.repository.AccountRepository;
import com.project.accounts.repository.CustomerRepository;
import com.project.accounts.services.framework.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;


    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> existingCustomer = customerRepository.findCustomerByMobileNumber(customer.getMobileNumber());
        if (existingCustomer.isPresent()) {
            throw new RuntimeException("Customer with mobile number " + customer.getMobileNumber() + " already exists");
        }
        customer.setCustomerId(Math.abs(new Random().nextLong()));
        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));


    }

    @Override
    public CustomerDto getAccountDetailsByMobileNumber(String mobileNumber) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("customer", "mobileNumber", mobileNumber)
        ));
        Optional<Account> account = Optional.ofNullable(accountRepository.findByCustomerId(customer.get().getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", mobileNumber)
        ));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer.get(), new CustomerDto());
        AccountsDto accountsDto = AccountMapper.mapToAccountsDto(account.get(), new AccountsDto());
        customerDto.setAccountDto(accountsDto);

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customer) {
        AccountsDto accountsDto = customer.getAccountDto();
        if(accountsDto != null) {
            Account account = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber().toString()));
            AccountMapper.mapToAccount(accountsDto, account);
            account = accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer originCustomer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customer, originCustomer);
            customerRepository.save(originCustomer);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }


    private Account createNewAccount(Customer customer) {
        Account account = new Account();
        account.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        account.setAccountNumber(randomAccNumber);
        account.setAccountType(AccountConstants.SAVINGS);
        account.setBranchAddress(AccountConstants.ADDRESS);
        return account;
    }

}
