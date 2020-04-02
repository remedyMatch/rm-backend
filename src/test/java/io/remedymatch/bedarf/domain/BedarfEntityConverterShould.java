package io.remedymatch.bedarf.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfEntityConverter soll")
public class BedarfEntityConverterShould {

	private static final BedarfId BEDARF_ID = new BedarfId(UUID.randomUUID());
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BigDecimal REST = BigDecimal.valueOf(120.0);
	private static final ArtikelEntity ARTIKEL = ArtikelEntity.builder().id(UUID.randomUUID()).build();
	private static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION = Institution.builder().id(INSTITUTION_ID).build();
	private static final InstitutionEntity INSTITUTION_ENTITY = InstitutionEntity.builder().id(INSTITUTION_ID.getValue()).build();
	private static final InstitutionStandortId STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT = InstitutionStandort.builder().id(STANDORT_ID).build();
	private static final InstitutionStandortEntity STANDORT_ENTITY = InstitutionStandortEntity.builder().id(STANDORT_ID.getValue()).build();
	private static final boolean STERIL = true;
	private static final boolean ORIGINALVERPACKT = true;
	private static final boolean MEDIZINISCH = true;
	private static final String KOMMENTAR = "Kommentar";
	private static final boolean BEDIENT = false;
	private static final BedarfAnfrageId ANFRAGE_ID = new BedarfAnfrageId(UUID.randomUUID());
	private static final BedarfAnfrage ANFRAGE = BedarfAnfrage.builder().id(ANFRAGE_ID).build();
	private static final BedarfAnfrageEntity ANFRAGE_ENTITY = BedarfAnfrageEntity.builder().id(ANFRAGE_ID.getValue())
			.build();

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(bedarf(true), BedarfEntityConverter.convert(entity(true)));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(BedarfEntityConverter.convert((BedarfEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt ohne Id in Entity konvertieren")
	void domain_Objekt_ohne_Id_in_Entity_konvertieren() {
		assertEquals(entity(false), BedarfEntityConverter.convert(bedarf(false)));
	}

	@Test
	@DisplayName("Domain Objekt mit Id in Entity konvertieren")
	void domain_Objekt_mit_Id_in_Entity_konvertieren() {
		assertEquals(entity(true), BedarfEntityConverter.convert(bedarf(true)));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(BedarfEntityConverter.convert((Bedarf) null));
	}

	private Bedarf bedarf(boolean mitId) {
		return Bedarf.builder() //
				.id(mitId ? BEDARF_ID : null) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL) //
				.institution(INSTITUTION) //
				.standort(STANDORT) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.anfragen(Arrays.asList(ANFRAGE)) //
				.build();
	}

	private BedarfEntity entity(boolean mitId) {
		return BedarfEntity.builder() //
				.id(mitId ? BEDARF_ID.getValue() : null) //
				.anzahl(ANZAHL) //
				.rest(REST) //
				.artikel(ARTIKEL) //
				.institution(INSTITUTION_ENTITY) //
				.standort(STANDORT_ENTITY) //
				.steril(STERIL) //
				.originalverpackt(ORIGINALVERPACKT) //
				.medizinisch(MEDIZINISCH) //
				.kommentar(KOMMENTAR) //
				.bedient(BEDIENT) //
				.anfragen(Arrays.asList(ANFRAGE_ENTITY)) //
				.build();
	}
}
