package io.remedymatch.bedarf.domain;

import java.math.BigDecimal;
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
public class Bedarf {
	private BedarfId id;
	private BigDecimal anzahl;
	private BigDecimal rest;
	private Artikel artikel;
	private Institution institution;
	private InstitutionStandort standort;
	private boolean steril;
	private boolean originalverpackt;
	private boolean medizinisch;
	private String kommentar;
	private boolean bedient;
    private transient BigDecimal entfernung;
	private List<BedarfAnfrage> anfragen;
}
