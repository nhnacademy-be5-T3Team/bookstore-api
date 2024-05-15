package com.t3t.bookstoreapi.book.annotation;

import com.t3t.bookstoreapi.book.validator.UniqueParticipantMapValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueParticipantMapValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueParticipantMap {
    String message() default "중복된 참여자와 역할 조합이 있습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
