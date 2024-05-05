package com.t3t.bookstoreapi.member.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bookstore_accounts")
@Getter
public class BookstoreAccount extends Account {
    @Column(name = "account_password")
    private String AccountPassword;

    public BookstoreAccount(String accountPassword) {
        AccountPassword = accountPassword;
    }

    public void modifyPassword(String newPassword) {
        this.AccountPassword = newPassword;
    }
}
