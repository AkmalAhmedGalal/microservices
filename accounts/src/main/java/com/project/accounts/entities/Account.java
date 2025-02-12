package com.project.accounts.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
@Entity
@Table(name = "account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE) //here we can use second level cache
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Account extends BaseEntity{

    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;

}