package io.remedymatch.institution.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;

@ExtendWith(SpringExtension.class)
@DisplayName("InstitutionEntityConverter soll")
public class InstitutionStandortEntityConverterShould {
	
	private static final InstitutionId INSTITUTION_ID = new InstitutionId(UUID.randomUUID());
	private static final String NAME = "Name";
	private static final String INSTITUTION_KEY = "institutionKey";
	private static final InstitutionTyp TYP = InstitutionTyp.Krankenhaus;
	
	private static final InstitutionStandortId INSTITUTION_STANDORT_ID = new InstitutionStandortId(UUID.randomUUID());
	
	@Test
	@DisplayName("Entity in Domain Objekt konvertieren")
	void entity_in_Domain_Objekt_konvertieren() {
		assertEquals(institution(), InstitutionEntityConverter.convert(entity()));
	}

	@Test
	@DisplayName("null Entity in null Domain Objekt konvertieren")
	void null_entity_in_null_Domain_Objekt_konvertieren() {
		assertNull(InstitutionEntityConverter.convert((InstitutionEntity) null));
	}

	@Test
	@DisplayName("Domain Objekt ohne Hauptstandort in Entity konvertieren")
	void domain_Objekt_ohne_Hauptstandort_in_Entity_konvertieren() {
		assertEquals(entity(), InstitutionEntityConverter.convert(institution()));
	}
	
	@Test
	@DisplayName("Domain Objekt mit Hauptstandort in Entity konvertieren")
	void domain_Objekt_mit_Hauptstandort_in_Entity_konvertieren() {
		InstitutionEntity entity = entity();
		entity.setHauptstandort(standortEntity());
		Institution institution = institution();
		institution.setHauptstandort(standort());
		assertEquals(entity, InstitutionEntityConverter.convert(institution));
	}

	@Test
	@DisplayName("Domain Objekt mit Standort in Entity konvertieren")
	void domain_Objekt_mit_Id_in_Entity_konvertieren() {
		InstitutionEntity entity = entity();
		entity.getStandorte().add(standortEntity());
		Institution institution = institution();
		institution.getStandorte().add(standort());
		assertEquals(entity, InstitutionEntityConverter.convert(institution));
	}

	@Test
	@DisplayName("null Domain Objekt in null Entity konvertieren")
	void null_domain_Objekt_in_Entity_konvertieren() {
		assertNull(InstitutionEntityConverter.convert((Institution) null));
	}
	
	private Institution institution() {
		return Institution.builder() //
				.id(INSTITUTION_ID) //
				.name(NAME) //
				.institutionKey(INSTITUTION_KEY) //
				.typ(TYP) //
				.build();
	}
	
	private InstitutionEntity entity() {
		return InstitutionEntity.builder() //
				.id(INSTITUTION_ID.getValue()) //
				.name(NAME) //
				.institutionKey(INSTITUTION_KEY) //
				.typ(TYP) //
				.build();
	}
	
	private InstitutionStandort standort() {
		return InstitutionStandort.builder() //
				.id(INSTITUTION_STANDORT_ID) //
				.build();
	}
	
	private InstitutionStandortEntity standortEntity() {
		return InstitutionStandortEntity.builder() //
				.id(INSTITUTION_STANDORT_ID.getValue()) //
				.build();
	}
}
