package io.remedymatch.institution.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
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
public class InstitutionAnfrage {
	private UUID id;
	private Institution institutionAn;
	private InstitutionStandort standortAn;
	private Institution institutionVon;
	private InstitutionStandort standortVon;
	private UUID angebotId;
	private UUID bedarfId;
	private ArtikelId artikelId;
	private ArtikelVarianteId artikelVarianteId;
	private BigDecimal anzahl;
	private String kommentar;
	private String prozessInstanzId;
	private String status;
	private transient BigDecimal entfernung;
}
