package io.remedymatch.bedarf.domain;

import java.math.BigDecimal;

import io.remedymatch.institution.domain.Institution;
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
public class BedarfAnfrage {
	private BedarfAnfrageId id;
	private Institution institutionVon;
	private InstitutionStandort standortVon;
	private Bedarf bedarf;
	private BigDecimal anzahl;
	private String kommentar;
	private String prozessInstanzId;
	private BedarfAnfrageStatus status;
}
