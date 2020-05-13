package io.remedymatch.nachricht.domain.service;

import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.nachricht.domain.model.Nachricht;
import io.remedymatch.nachricht.domain.model.NachrichtId;
import io.remedymatch.nachricht.infrastructure.Konversation2InstitutionEntity;
import io.remedymatch.nachricht.infrastructure.NachrichtEntity;
import io.remedymatch.person.domain.model.PersonId;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

public class NachrichtEntityConverter {

    static Nachricht convert(NachrichtEntity entity, InstitutionEntity institution) {
        return Nachricht.builder()
                .id(new NachrichtId(entity.getId()))
                .nachricht(entity.getNachricht())
                .ersteller(new PersonId(entity.getCreatedBy()))
                .erstellerInstitution(new InstitutionId(entity.getErstellerInstitution()))
                .erstellerName(institution.getName())
                .erstelltAm(entity.getCreatedDate())
                .build();
    }

    static List<Nachricht> convert(List<NachrichtEntity> entities, List<Konversation2InstitutionEntity> beteiligte) {
        val insitutionMap = beteiligte.stream().collect(Collectors.toMap(b -> b.getInstitution().getId(), b -> b));
        return entities.stream().map(n -> convert(n, insitutionMap.get(n.getErstellerInstitution()).getInstitution())).collect(Collectors.toList());
    }
}
