package io.remedymatch.match.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.api.InstitutionRO;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.match.domain.Match;
import io.remedymatch.match.domain.MatchId;
import io.remedymatch.match.domain.MatchStandort;
import io.remedymatch.match.domain.MatchStandortId;
import io.remedymatch.match.domain.MatchStatus;

@ExtendWith(SpringExtension.class)
@DisplayName("MatchStandortMapper soll")
public class MatchMapperShould {

	private static final MatchId MATCH_ID = new MatchId(UUID.randomUUID());
	private static final String KOMMENTAR = "Kommentar";
	private static final InstitutionId INSTITUTION_VON_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_VON = Institution.builder().id(INSTITUTION_VON_ID).build();
	private static final InstitutionRO INSTITUTION_VON_DTO = InstitutionRO.builder().id(INSTITUTION_VON_ID.getValue())
			.build();
	private static final MatchStandortId STANDORT_VON_ID = new MatchStandortId(UUID.randomUUID());
	private static final MatchStandort STANDORT_VON = MatchStandort.builder().id(STANDORT_VON_ID).build();
	private static final MatchStandortRO STANDORT_VON_DTO = MatchStandortRO.builder().id(STANDORT_VON_ID.getValue())
			.build();
	private static final InstitutionId INSTITUTION_AN_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_AN = Institution.builder().id(INSTITUTION_AN_ID).build();
	private static final InstitutionRO INSTITUTION_AN_DTO = InstitutionRO.builder().id(INSTITUTION_AN_ID.getValue())
			.build();
	private static final MatchStandortId STANDORT_AN_ID = new MatchStandortId(UUID.randomUUID());
	private static final MatchStandort STANDORT_AN = MatchStandort.builder().id(STANDORT_AN_ID).build();
	private static final MatchStandortRO STANDORT_AN_DTO = MatchStandortRO.builder().id(STANDORT_AN_ID.getValue())
			.build();
	private static final UUID ANFRAGE_ID = UUID.randomUUID();
	private static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	private static final MatchStatus STATUS = MatchStatus.Offen;

	@Test
	@DisplayName("Domain Objekt in DTO konvertieren")
	void domain_Objekt_in_DTO_konvertieren() {
		assertEquals(matchDTO(), MatchMapper.mapToDTO(match()));
	}

	@Test
	@DisplayName("null Domain Objekt in null DTO konvertieren")
	void null_Domain_Objekt_in_null_DTO_konvertieren() {
		assertNull(MatchStandortMapper.mapToDTO((MatchStandort) null));
	}

	private MatchDTO matchDTO() {
		return MatchDTO.builder() //
				.id(MATCH_ID.getValue()) //
				.anfrageId(ANFRAGE_ID) //
				.institutionVon(INSTITUTION_VON_DTO) //
				.standortVon(STANDORT_VON_DTO) //
				.institutionAn(INSTITUTION_AN_DTO) //
				.standortAn(STANDORT_AN_DTO) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.build();
	}

	private Match match() {
		return Match.builder() //
				.id(MATCH_ID) //
				.anfrageId(ANFRAGE_ID) //
				.institutionVon(INSTITUTION_VON) //
				.standortVon(STANDORT_VON) //
				.institutionAn(INSTITUTION_AN) //
				.standortAn(STANDORT_AN) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.build();
	}
}
