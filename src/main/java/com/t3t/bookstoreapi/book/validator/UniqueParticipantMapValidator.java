package com.t3t.bookstoreapi.book.validator;

import com.t3t.bookstoreapi.book.annotation.UniqueParticipantMap;
import com.t3t.bookstoreapi.book.model.dto.ParticipantMapDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueParticipantMapValidator implements ConstraintValidator<UniqueParticipantMap, List<ParticipantMapDto>> {

    @Override
    public void initialize(UniqueParticipantMap constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<ParticipantMapDto> participantMapList, ConstraintValidatorContext context) {
        if (participantMapList == null || participantMapList.isEmpty()) {
            return true;
        }

        Set<ParticipantMapDto> participantMapSet = new HashSet<>();
        for (ParticipantMapDto participantMapDto : participantMapList) {
            if (!participantMapSet.add(participantMapDto)) {
                return false;
            }
        }
        return true;
    }
}
