package io.remedymatch.match.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionId;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.match.infrastructure.MatchEntity;
import io.remedymatch.match.infrastructure.MatchStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("MatchEntityConverter soll")
public class MatchEntityConverterShould {

	private static final MatchId MATCH_ID = new MatchId(UUID.randomUUID());
	private static final String KOMMENTAR = "Kommentar";
	private static final InstitutionId INSTITUTION_VON_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_VON = Institution.builder().id(INSTITUTION_VON_ID).build();
	private static final InstitutionEntity INSTITUTION_VON_ENTITY = InstitutionEntity.builder().id(INSTITUTION_VON_ID.getValue()).build();
	private static final MatchStandortId STANDORT_VON_ID = new MatchStandortId(UUID.randomUUID());
	private static final MatchStandort STANDORT_VON = MatchStandort.builder().id(STANDORT_VON_ID).build();
	private static final MatchStandortEntity STANDORT_VON_ENTITY = MatchStandortEntity.builder()
			.id(STANDORT_VON_ID.getValue()).build();
	private static final InstitutionId INSTITUTION_AN_ID = new InstitutionId(UUID.randomUUID());
	private static final Institution INSTITUTION_AN = Institution.builder().id(INSTITUTION_AN_ID).build();
	private static final InstitutionEntity INSTITUTION_AN_ENTITY = InstitutionEntity.builder().id(INSTITUTION_AN_ID.getValue()).build();
	private static final MatchStandortId STANDORT_AN_ID = new MatchStandortId(UUID.randomUUID());
	private static final MatchStandort STANDORT_AN = MatchStandort.builder().id(STANDORT_AN_ID).build();
	private static final MatchStandortEntity STANDORT_AN_ENTITY = MatchStandortEntity.builder()
			.id(STANDORT_AN_ID.getValue()).build();
	private static final UUID ANFRAGE_ID = UUID.randomUUID();
	private static final String PROZESSINSTANZ_ID = "ProzessInstanzId";
	private static final MatchStatus STATUS = MatchStatus.Offen;

	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(match(true), MatchEntityConverter.convert(entity(true)));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(MatchEntityConverter.convert((MatchEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt mit Id in Entity konvertieren")
	void domain_Objekt_mit_Id_in_Entity_konvertieren() {
		assertEquals(entity(true), MatchEntityConverter.convert(match(true)));
	}

	@Test
	@DisplayName("Domain Objekt ohne Id in Entity konvertieren")
	void domain_Objekt_ohne_Id_in_Entity_konvertieren() {
		assertEquals(entity(false), MatchEntityConverter.convert(match(false)));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(MatchEntityConverter.convert((Match) null));
	}

	private Match match(boolean mitId) {
		return Match.builder() //
				.id(mitId ? MATCH_ID : null) //
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

	private MatchEntity entity(boolean mitId) {
		return MatchEntity.builder() //
				.id(mitId ? MATCH_ID.getValue() : null) //
				.anfrageId(ANFRAGE_ID) //
				.institutionVon(INSTITUTION_VON_ENTITY) //
				.standortVon(STANDORT_VON_ENTITY) //
				.institutionAn(INSTITUTION_AN_ENTITY) //
				.standortAn(STANDORT_AN_ENTITY) //
				.kommentar(KOMMENTAR) //
				.prozessInstanzId(PROZESSINSTANZ_ID) //
				.status(STATUS) //
				.build();
	}
}

