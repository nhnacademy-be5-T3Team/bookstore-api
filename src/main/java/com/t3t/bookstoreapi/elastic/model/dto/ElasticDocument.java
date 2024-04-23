package com.t3t.bookstoreapi.elastic.model.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Document(indexName = "t3t_book")
public class ElasticDocument {
    @Id
    private String id;
    @Field(name = "book_id")
    private BigDecimal bookId;
    @Field(name = "book_name")
    private String name;
    @Field(name = "book_price")
    private BigDecimal price;
    @Field(name = "book_discount")
    private BigDecimal discount;
    @Field(name = "book_published")
    private String published;
    @Field(name = "book_average_score")
    private Float averageScore;
    @Field(name = "book_like_count")
    private Integer likeCount;
    @Field(name = "publisher_name")
    private String publisher;
    @Field(name = "thumbnail_image_url")
    private String coverImageUrl;
    @Field(name = "participant_name")
    private String authorName;
    @Field(name = "participant_role_name_kr")
    private String authorRole;
}
