package com.t3t.bookstoreapi.book.util;

import java.math.BigDecimal;

public class BookServiceUtils {

    private BookServiceUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountRate) {
        BigDecimal discountPercentage = discountRate.divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = originalPrice.multiply(discountPercentage);

        return originalPrice.subtract(discountAmount);
    }
}