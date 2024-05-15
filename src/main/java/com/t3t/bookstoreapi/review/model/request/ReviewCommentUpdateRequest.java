package com.t3t.bookstoreapi.review.model.request;

import lombok.*;

/**
 * 리뷰 comment 업데이트 요청을 나타내는 객체
 * @author Yujin-nKim(김유진)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCommentUpdateRequest {
    String comment;
}
