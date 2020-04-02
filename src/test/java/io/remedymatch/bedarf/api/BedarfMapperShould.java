package io.remedymatch.bedarf.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.artikel.domain.Artikel;
import io.remedymatch.artikel.domain.ArtikelId;
import io.remedymatch.bedarf.domain.Bedarf;
import io.remedymatch.bedarf.domain.BedarfId;
import io.remedymatch.bedarf.domain.NeuesBedarf;
import io.remedymatch.institution.api.InstitutionStandortDTO;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfMapper soll")
public class BedarfMapperShould {

	private static final BedarfId BEDARF_ID = new BedarfId(UUID.randomUUID());
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BigDecimal REST = BigDecimal.valueOf(120.0);
	private static final ArtikelId ARTIKEL_ID = new ArtikelId(UUID.randomUUID());
	private static final Artikel ARTIKEL = Artikel.builder().id(ARTIKEL_ID).build();
	private static final ArtikelDTO ARTIKEL_DTO = ArtikelDTO.builder().id(ARTIKEL_ID.getValue()).build();
	private static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandortDTO STANDORT_DTO = InstitutionStandortDTO.builder()
			.id(STANDORT_ID.getValue()).build();
	private static final InstitutionStandort STANDORT = InstitutionStandort.builder().id(STANDORT_ID).build();
	private static final boolean STERIL = true;
	private static final boolean ORIGINALVERPACKT = true;
	private static final boolean MEDIZINISCH = true;
	private static final String KOMMENTAR = "Kommentar";
	private static final boolean BEDIENT = false;

	@Test
	@DisplayName("NeueBedarf konvertieren koennen")
	void neuesBedarf_konvertieren_koennen() {
		assertEquals(neueBedarf(), BedarfMapper.mapToNeuesBedarf(neuesBedarfRequest()));
	}

	@Test
	@DisplayName("eine IllegalArgumentException wenn NeuesBedarfRequest null ist")
	void eine_IllegalArgumentException_werfen_wenn_NeuesBedarfRequest_null_ist() {
		assertThrows(IllegalArgumentException.class, //
				() -> BedarfMapper.mapToNeuesBedarf(null));
	}

	@Test
	@DisplayName("Dto in Domain Objekt mappen")
	void dto_in_Domain_Objekt_konvertieren() {
		assertEquals(bedarf(), BedarfMapper.mapToBedarf(dto()));
	}

	@Test
	@DisplayName("null DTO in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(BedarfMapper.mapToBedarf((BedarfDTO) null));
	}

	@Test
	@DisplayName("Domain Objekt in DTO konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(dto(), BedarfMapper.mapToDTO(bedarf()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(BedarfMapper.mapToDTO((Bedarf) null));
	}

	/* help methods */

	private NeuesBedarf neueBedarf() {
		return NeuesBedarf.builder() //
				.anzahl(ANZAHL) //
				.artikelId(ARTIKEL_ID) //
				.standortId(STANDORT_ID) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.build();
	}

	private NeuesBedarfRequest neuesBedarfRequest() {
		return NeuesBedarfRequest.builder() //
				.anzahl(ANZAHL) //
				.artikelId(ARTIKEL_ID.getValue()) //
				.standortId(STANDORT_ID.getValue()) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.build();
	}

	private Bedarf bedarf() {
		return Bedarf.builder() //
				.id(BEDARF_ID) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL) //
				.standort(STANDORT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.build();
	}

	private BedarfDTO dto() {
		return BedarfDTO.builder() //
				.id(BEDARF_ID.getValue()) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL_DTO) //
				.standort(STANDORT_DTO) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.build();
	}
}
