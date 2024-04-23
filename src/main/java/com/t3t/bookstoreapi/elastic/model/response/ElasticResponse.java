package com.t3t.bookstoreapi.elastic.model.response;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ElasticResponse {
    private BigDecimal bookId;
    private String name;
    private BigDecimal price;
    private BigDecimal discountRate;
    private BigDecimal discountPrice;
    private String published;
    private Float averageScore;
    private Integer likeCount;
    private String publisher;
    private String coverImageUrl;
    private String authorName;
    private String authorRole;
    private float score;



}
