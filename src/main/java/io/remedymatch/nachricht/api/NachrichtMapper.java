package io.remedymatch.nachricht.api;

import io.remedymatch.nachricht.domain.model.Nachricht;
import io.remedymatch.nachricht.domain.model.NeueNachricht;

import java.util.List;
import java.util.stream.Collectors;

public class NachrichtMapper {

    public static NeueNachricht map(NeueNachrichtRO ro) {
        return NeueNachricht.builder()
                .nachricht(ro.getNachricht())
                .build();
    }

    public static NachrichtRO map(Nachricht nachricht) {
        return NachrichtRO.builder()
                .id(nachricht.getId())
                .erstelltAm(nachricht.getErstelltAm())
                .nachricht(nachricht.getNachricht())
                .referenzId(nachricht.getReferenzId().getValue())
                .referenzTyp(nachricht.getReferenzTyp())
                .von(nachricht.getVon().getValue())
                .build();
    }

    public static List<NachrichtRO> map(List<Nachricht> nachrichten) {
        return nachrichten.stream().map(NachrichtMapper::map).collect(Collectors.toList());
    }
}
