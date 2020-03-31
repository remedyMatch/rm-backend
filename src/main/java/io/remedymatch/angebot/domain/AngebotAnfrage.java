package io.remedymatch.angebot.domain;

import java.math.BigDecimal;

import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
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
	private String kommentar;
	private InstitutionEntity institutionVon;
	private InstitutionEntity institutionAn;
	private Angebot angebot;
	private InstitutionStandortEntity standortVon;
	private InstitutionStandortEntity standortAn;
	private String prozessInstanzId;
	private BigDecimal anzahl;
	private AngebotAnfrageStatus status;
}
