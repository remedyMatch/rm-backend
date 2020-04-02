package io.remedymatch.angebot.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import io.remedymatch.artikel.domain.Artikel;
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
public class Angebot {
	private AngebotId id;
	private BigDecimal anzahl;
	private BigDecimal rest;
	private Artikel artikel;
	private Institution institution;
	private InstitutionStandort standort;
	private LocalDateTime haltbarkeit;
	private boolean steril;
	private boolean originalverpackt;
	private boolean medizinisch;
	private String kommentar;
	private boolean bedient;
    private transient BigDecimal entfernung;
	private List<AngebotAnfrage> anfragen;
}
