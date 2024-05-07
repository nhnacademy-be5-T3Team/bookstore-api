package com.t3t.bookstoreapi.membergradepolicies.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateMemberGradePolicyRequest {

    private BigDecimal startAmount;
    private BigDecimal endAmount;
    private int rate;

}
