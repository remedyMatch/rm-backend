package io.remedymatch.bedarf.domain;

import java.math.BigDecimal;

import io.remedymatch.artikel.domain.ArtikelId;
import io.remedymatch.institution.domain.InstitutionStandortId;
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
public class NeuesBedarf {
	private BigDecimal anzahl;
	private ArtikelId artikelId;
	private InstitutionStandortId standortId;
	private boolean steril;
	private boolean originalverpackt;
	private boolean medizinisch;
	private String kommentar;
}
