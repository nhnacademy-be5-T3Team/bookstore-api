package com.t3t.bookstoreapi.category.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    @NotNull
    @Column(name = "category_name")
    private String categoryName;

}
