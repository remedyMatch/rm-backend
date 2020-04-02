package io.remedymatch.bedarf.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.bedarf.infrastructure.BedarfAnfrageEntity;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("BedarfAnfrageEntityConverter soll")
public class BedarfAnfrageEntityConverterShould {

	private static final BedarfAnfrageId BEDARF_ANFRAGE_ID = new BedarfAnfrageId(UUID.randomUUID());
	private static final String KOMMENTAR = "Kommentar";
	private static final InstitutionId INSTITUTION_VON_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_VON = Institution.builder().id(INSTITUTION_VON_ID).build();
	private static final InstitutionEntity INSTITUTION_VON_ENTITY = InstitutionEntity.builder().id(INSTITUTION_VON_ID.getValue()).build();
	private static final InstitutionStandortId STANDORT_VON_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final InstitutionStandort STANDORT_VON = InstitutionStandort.builder().id(STANDORT_VON_ID).build();
	private static final InstitutionStandortEntity STANDORT_VON_ENTITY = InstitutionStandortEntity.builder()
			.id(STANDORT_VON_ID.getValue()).build();
	private static final BedarfId BEDARF_ID = new BedarfId(UUID.randomUUID());
	private static final Bedarf BEDARF = Bedarf.builder().id(BEDARF_ID).build();
	private static final BedarfEntity BEDARF_ENTITY = BedarfEntity.builder().id(BEDARF_ID.getValue()).build();
	private static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	private static final BigDecimal ANZAHL = BigDecimal.valueOf(120.0);
	private static final BedarfAnfrageStatus STATUS = BedarfAnfrageStatus.Offen;

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(bedarfAnfrage(true), BedarfAnfrageEntityConverter.convert(entity(true)));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(BedarfAnfrageEntityConverter.convert((BedarfAnfrageEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt mit Id in Entity konvertieren")
	void domain_Objekt_mit_Id_in_Entity_konvertieren() {
		assertEquals(entity(true), BedarfAnfrageEntityConverter.convert(bedarfAnfrage(true)));
	}

	@Test
	@DisplayName("Domain Objekt ohne Id in Entity konvertieren")
	void domain_Objekt_ohne_Id_in_Entity_konvertieren() {
		assertEquals(entity(false), BedarfAnfrageEntityConverter.convert(bedarfAnfrage(false)));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(BedarfAnfrageEntityConverter.convert((BedarfAnfrage) null));
	}

	private BedarfAnfrage bedarfAnfrage(boolean mitId) {
		return BedarfAnfrage.builder() //
				.id(mitId ? BEDARF_ANFRAGE_ID : null) //
				.bedarf(BEDARF) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.build();
	}

	private BedarfAnfrageEntity entity(boolean mitId) {
		return BedarfAnfrageEntity.builder() //
				.id(mitId ? BEDARF_ANFRAGE_ID.getValue() : null) //
				.bedarf(BEDARF_ENTITY) //
				.institutionVon(INSTITUTION_VON_ENTITY) //
				.standortVon(STANDORT_VON_ENTITY) //
				.anzahl(ANZAHL) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.build();
	}
}
