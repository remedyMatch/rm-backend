package io.remedymatch.angebot.api;

import io.remedymatch.angebot.domain.MengenTyp;
import io.remedymatch.artikel.api.ArtikelDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AngebotDTO {
    private UUID id;
    private ArtikelDTO artikel;
    private MengenTyp mengenTyp;
    private double anzahl;

}
