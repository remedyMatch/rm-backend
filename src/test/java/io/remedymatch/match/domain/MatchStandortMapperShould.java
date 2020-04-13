package io.remedymatch.match.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.institution.domain.model.InstitutionStandortId;

@ExtendWith(SpringExtension.class)
@DisplayName("MatchStandortMapper soll")
public class MatchStandortMapperShould {

	private static final UUID STANDORT_ID = UUID.randomUUID();
	private static final InstitutionStandortId INSTITUTION_STANDORT_ID = new InstitutionStandortId(STANDORT_ID);
	private static final String NAME = "Name";
	private static final String PLZ = "PLZ";
	private static final String ORT = "Ort";
	private static final String STRASSE = "Strasse";
	private static final String LAND = "Land";
	private static final BigDecimal LONGITUDE = BigDecimal.valueOf(123);
	private static final BigDecimal LATITUDE = BigDecimal.valueOf(555);

	@Test
	@DisplayName("InstitutionStandort in MatchStandort konvertieren")
	void institutionStandort_in_MatchStandort_konvertieren() {
		assertEquals(matchStandort(), MatchStandortMapper.mapToMatchStandort(institutionStandort()));
	}

	@Test
	@DisplayName("null InstitutionStandort in null MatchStandort konvertieren")
	void null_InstitutionStandort_in_null_MatchStandort_konvertieren() {
		assertNull(MatchStandortMapper.mapToMatchStandort((InstitutionStandort) null));
	}

	private InstitutionStandort institutionStandort() {
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

	private MatchStandort matchStandort() {
		return MatchStandort.builder() //
				.institutionStandortId(INSTITUTION_STANDORT_ID.getValue()) //
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
