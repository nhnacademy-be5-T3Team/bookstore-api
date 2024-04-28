package com.t3t.bookstoreapi.recommendation.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 추천 목록 조회시에 사용되는 간단한 도서 정보를 담는 데이터 전송 객체(DTO) <br>
 *
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookInfoBriefResponse {
    private Long id; // 도서 id
    private String name; // 도서 제목
    private String thumbnailImageUrl; // 도서 커버 이미지 url
}
