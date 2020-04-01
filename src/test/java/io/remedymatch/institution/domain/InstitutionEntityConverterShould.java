package io.remedymatch.institution.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionEntityConverter soll")
public class InstitutionEntityConverterShould {

	private static final InstitutionStandortId INSTITUTION_STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	private static final String NAME = "Name";
	private static final String PLZ = "PLZ";
	private static final String ORT = "Ort";
	private static final String STRASSE = "Strasse";
	private static final String LAND = "Land";
	private static final BigDecimal LONGITUDE = BigDecimal.valueOf(123);
	private static final BigDecimal LATITUDE = BigDecimal.valueOf(555);

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(standort(), InstitutionStandortEntityConverter.convert(entity()));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(InstitutionStandortEntityConverter.convert((InstitutionStandortEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(entity(), InstitutionStandortEntityConverter.convert(standort()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(InstitutionStandortEntityConverter.convert((InstitutionStandort) null));
	}

	private InstitutionStandort standort() {
		return InstitutionStandort.builder() //
				.id(INSTITUTION_STANDORT_ID) //
				.name(NAME) //
				.plz(PLZ) //
				.ort(ORT) //
				.strasse(STRASSE) //
				.land(LAND) //
				.longitude(LONGITUDE) //
				.latitude(LATITUDE) //
				.build();
	}

	private InstitutionStandortEntity entity() {
		return InstitutionStandortEntity.builder() //
				.id(INSTITUTION_STANDORT_ID.getValue()) //
				.name(NAME) //
				.plz(PLZ) //
				.ort(ORT) //
				.strasse(STRASSE) //
				.land(LAND) //
				.longitude(LONGITUDE) //
				.latitude(LATITUDE) //
				.build();
	}
}
