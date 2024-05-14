package com.t3t.bookstoreapi.book.model.response;

import lombok.*;

@Builder@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BookCouponResponse {
    private long bookId;
}
