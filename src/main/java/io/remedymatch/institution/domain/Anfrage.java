package io.remedymatch.institution.domain;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Anfrage {
    private UUID id;
	private Institution institutionAn;
	private InstitutionStandort standortAn;
	private Institution institutionVon;
	private InstitutionStandort standortVon;
	private UUID angebotId;
	private UUID bedarfId;
	private BigDecimal anzahl;
	private String kommentar;
	private String prozessInstanzId;
	private String status;
    private transient BigDecimal entfernung;
}
