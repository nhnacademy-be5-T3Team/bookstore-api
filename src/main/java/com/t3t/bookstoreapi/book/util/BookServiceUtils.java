package com.t3t.bookstoreapi.book.util;

import com.t3t.bookstoreapi.book.model.entity.ParticipantRoleRegistration;
import com.t3t.bookstoreapi.book.model.response.AuthorInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BookServiceUtils {

    private BookServiceUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static List<AuthorInfo> extractAuthorInfo(List<ParticipantRoleRegistration> authorList) {
        return authorList.stream()
                .map(participantRole -> AuthorInfo.builder()
                        .role(participantRole.getParticipantRole().getParticipantRoleNameKr())
                        .name(participantRole.getParticipant().getParticipantName())
                        .build())
                .collect(Collectors.toList());
    }


    public static BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountRate) {
        BigDecimal discountPercentage = discountRate.divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = originalPrice.multiply(discountPercentage);

        return originalPrice.subtract(discountAmount);
    }
}