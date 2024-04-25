package com.t3t.bookstoreapi.member.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

/**
 * 회원 계정 엔티티 클래스
 * @apiNote Account의 서브타입에 해당하는 엔티티로 `BookstoreAccount`, `OAuthAccount` 가 존재한다.
 * @see BookstoreAccount
 * @author woody35545(구건모)
 */
@SuperBuilder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_id")
    @Comment("계정 식별자")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @Comment("계정 소유 회원")
    private Member member;

    @Column(name = "deleted", columnDefinition = "TINYINT(1) DEFAULT 0", nullable = false)
    @Comment("삭제 여부")
    private boolean deleted;
}
