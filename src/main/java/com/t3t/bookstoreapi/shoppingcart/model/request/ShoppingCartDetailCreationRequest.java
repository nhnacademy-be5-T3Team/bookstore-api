package com.t3t.bookstoreapi.shoppingcart.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShoppingCartDetailCreationRequest {
    @NotNull
    private Long bookId;
    @NotNull
    @Min(1)
    private Long quantity;
}