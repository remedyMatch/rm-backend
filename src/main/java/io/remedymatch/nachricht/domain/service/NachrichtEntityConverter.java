package io.remedymatch.nachricht.domain.service;

import io.remedymatch.nachricht.domain.model.Nachricht;
import io.remedymatch.nachricht.domain.model.NachrichtId;
import io.remedymatch.nachricht.domain.model.NachrichtReferenz;
import io.remedymatch.nachricht.infrastructure.NachrichtEntity;
import io.remedymatch.person.domain.model.PersonId;

import java.util.List;
import java.util.stream.Collectors;

public class NachrichtEntityConverter {

    static Nachricht convert(NachrichtEntity entity) {
        return Nachricht.builder()
                .id(new NachrichtId(entity.getId()))
                .nachricht(entity.getNachricht())
                .referenzId(new NachrichtReferenz(entity.getReferenzId()))
                .referenzTyp(entity.getReferenzTyp())
                .von(new PersonId(entity.getCreatedBy()))
                .erstelltAm(entity.getCreatedDate())
                .build();
    }

    static List<Nachricht> convert(List<NachrichtEntity> entities) {
        return entities.stream().map(NachrichtEntityConverter::convert).collect(Collectors.toList());
    }
}
