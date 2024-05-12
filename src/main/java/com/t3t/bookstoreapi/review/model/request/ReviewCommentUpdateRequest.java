package com.t3t.bookstoreapi.review.model.request;

import lombok.Builder;
import lombok.Getter;

/**
 * 리뷰 comment 업데이트 요청을 나타내는 객체
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
public class ReviewCommentUpdateRequest {
    String comment;
}
