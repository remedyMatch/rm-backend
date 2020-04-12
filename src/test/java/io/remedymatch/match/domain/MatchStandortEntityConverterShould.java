package io.remedymatch.match.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.match.infrastructure.MatchStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("MatchStandortEntityConverter soll")
public class MatchStandortEntityConverterShould {

	private static final MatchStandortId MATCH_STANDORT_ID = new MatchStandortId(UUID.randomUUID());
	private static final UUID INSTITUTION_STANDORT_ID = UUID.randomUUID();
	private static final String NAME = "Name";
	private static final String STRASSE = "Strasse";
	private static final String HAUSNUMMER = "10a";
	private static final String PLZ = "PLZ";
	private static final String ORT = "Ort";
	private static final String LAND = "Land";
	private static final BigDecimal LONGITUDE = BigDecimal.valueOf(123);
	private static final BigDecimal LATITUDE = BigDecimal.valueOf(555);

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(standort(), MatchStandortEntityConverter.convert(entity()));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(MatchStandortEntityConverter.convert((MatchStandortEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt in Entity konvertieren")
	void domain_Objekt_in_Entity_konvertieren() {
		assertEquals(entity(), MatchStandortEntityConverter.convert(standort()));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(MatchStandortEntityConverter.convert((MatchStandort) null));
	}

	private MatchStandort standort() {
		return MatchStandort.builder() //
				.id(MATCH_STANDORT_ID) //
				.institutionStandortId(INSTITUTION_STANDORT_ID) //
				.name(NAME) //
				.strasse(STRASSE) //
				.hausnummer(HAUSNUMMER) //
				.plz(PLZ) //
				.ort(ORT) //
				.land(LAND) //
				.longitude(LONGITUDE) //
				.latitude(LATITUDE) //
				.build();
	}

	private MatchStandortEntity entity() {
		return MatchStandortEntity.builder() //
				.id(MATCH_STANDORT_ID.getValue()) //
				.institutionStandortId(INSTITUTION_STANDORT_ID) //
				.name(NAME) //
				.strasse(STRASSE) //
				.hausnummer(HAUSNUMMER) //
				.plz(PLZ) //
				.ort(ORT) //
				.land(LAND) //
				.longitude(LONGITUDE) //
				.latitude(LATITUDE) //
				.build();
	}
}
