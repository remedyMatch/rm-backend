package io.remedymatch.angebot.domain;

import java.math.BigDecimal;

import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandort;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AngebotAnfrage {
	private AngebotAnfrageId id;
	private InstitutionEntity institutionVon;
	private InstitutionStandort standortVon;
	private Angebot angebot;
	private BigDecimal anzahl;
	private String kommentar;
	private String prozessInstanzId;
	private AngebotAnfrageStatus status;
}
