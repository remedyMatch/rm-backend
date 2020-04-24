package io.remedymatch.match.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.match.domain.MatchStandort;
import io.remedymatch.match.domain.MatchStandortId;

@ExtendWith(SpringExtension.class)
@DisplayName("MatchStandortMapper soll")
public class MatchStandortMapperShould {

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
	@DisplayName("Domain Objekt in DTO konvertieren")
	void domain_Objekt_in_DTO_konvertieren() {
		assertEquals(matchStandortDTO(), MatchStandortMapper.mapToDTO(matchStandort()));
	}

	@Test
	@DisplayName("null Domain Objekt in null DTO konvertieren")
	void null_Domain_Objekt_in_null_DTO_konvertieren() {
		assertNull(MatchStandortMapper.mapToDTO((MatchStandort) null));
	}

	private MatchStandortRO matchStandortDTO() {
		return MatchStandortRO.builder() //
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

	private MatchStandort matchStandort() {
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
}
