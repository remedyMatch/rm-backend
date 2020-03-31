package io.remedymatch.angebot.domain;

import java.util.UUID;

import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AngebotAnfrage {

	private UUID id;
	private String kommentar;
	private InstitutionEntity institutionVon;
	private InstitutionEntity institutionAn;
	private AngebotEntity angebot;
	private InstitutionStandortEntity standortVon;
	private InstitutionStandortEntity standortAn;
	private String prozessInstanzId;
	private double anzahl;
	private AngebotAnfrageStatus status;

}
