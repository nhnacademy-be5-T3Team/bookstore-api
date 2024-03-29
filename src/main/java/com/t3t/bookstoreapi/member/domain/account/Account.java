package com.t3t.bookstoreapi.member.domain.account;

import com.t3t.bookstoreapi.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @Column(name = "account_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Account(String id, Member member) {
        this.id = id;
        this.member = member;
    }
}
