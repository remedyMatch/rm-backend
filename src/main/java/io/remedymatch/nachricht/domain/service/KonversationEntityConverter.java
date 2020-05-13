package io.remedymatch.nachricht.domain.service;

import io.remedymatch.nachricht.domain.model.Konversation;
import io.remedymatch.nachricht.domain.model.KonversationId;
import io.remedymatch.nachricht.domain.model.NachrichtReferenz;
import io.remedymatch.nachricht.infrastructure.KonversationEntity;

import java.util.List;
import java.util.stream.Collectors;

public class KonversationEntityConverter {

    static Konversation convert(KonversationEntity entity) {
        return Konversation.builder()
                .id(new KonversationId(entity.getId()))
                .referenzId(new NachrichtReferenz(entity.getReferenzId()))
                .referenzTyp(entity.getReferenzTyp())
                .nachrichten(NachrichtEntityConverter.convert(entity.getNachrichten(), entity.getBeteiligte()))
                .beteiligte(entity.getBeteiligte().stream().map(b -> b.getInstitution().getName()).collect(Collectors.toList()))
                .build();
    }

    static List<Konversation> convert(List<KonversationEntity> entities) {
        return entities.stream().map(KonversationEntityConverter::convert).collect(Collectors.toList());
    }


}
