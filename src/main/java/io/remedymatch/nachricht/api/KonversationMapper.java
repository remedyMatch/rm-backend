package io.remedymatch.nachricht.api;

import io.remedymatch.nachricht.domain.model.Konversation;
import io.remedymatch.nachricht.domain.model.NeueNachricht;

import java.util.List;
import java.util.stream.Collectors;

public class KonversationMapper {

    public static NeueNachricht map(NeueNachrichtRO ro) {
        return NeueNachricht.builder()
                .nachricht(ro.getNachricht())
                .build();
    }

    public static KonversationRO map(Konversation konversation) {
        return KonversationRO.builder()
                .id(konversation.getId().getValue())
                .beteiligte(konversation.getBeteiligte())
                .referenzId(konversation.getReferenzId().getValue())
                .referenzTyp(konversation.getReferenzTyp())
                .nachrichten(NachrichtMapper.map(konversation.getNachrichten()))
                .build();
    }

    public static List<KonversationRO> map(List<Konversation> konversationen) {
        return konversationen.stream().map(KonversationMapper::map).collect(Collectors.toList());
    }
}
