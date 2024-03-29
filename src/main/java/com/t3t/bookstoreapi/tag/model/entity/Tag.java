package com.t3t.bookstoreapi.tag.model.entity;

import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer tagId;

    @NotNull
    @Column(name = "tag_name")
    private String tagName;
}
