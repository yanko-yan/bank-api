package com.yankoyan.spring.bank_api.model;

import com.yankoyan.spring.bank_api.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_numbers")
    @SequenceGenerator(
            name = "account_numbers",
            sequenceName = "account_numbers",
            allocationSize = 1
    )
    private Long number;
    private BigDecimal balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @Column(name = "opening_date")
    private LocalDate openingDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Account (User owner) {
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
        this.openingDate = LocalDate.now();
        this.owner = owner;
    }
}
