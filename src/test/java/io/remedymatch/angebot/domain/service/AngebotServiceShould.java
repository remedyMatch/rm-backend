package io.remedymatch.angebot.domain.service;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageJpaRepository;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.institution.domain.Institution;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionStandort;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.user.domain.UserService;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = { //
		AngebotService.class, //
		AngebotJpaRepository.class, //
		AngebotAnfrageJpaRepository.class, //
		UserService.class, //
		AngebotAnfrageProzessService.class //
})
@Tag("Spring")
@DisplayName("AngebotService soll")
@Disabled
public class AngebotServiceShould {
	@Autowired
	private AngebotService angebotService;

	@MockBean
	private AngebotJpaRepository angebotRepository;

	@MockBean
	private AngebotAnfrageJpaRepository anfrageRepository;

	@MockBean
	private UserService userService;

	@MockBean
	private AngebotAnfrageProzessService anfrageProzessService;

	/* help method */

	private Institution institution(final InstitutionStandort hauptstandort) {
		return Institution.builder() //
				.id(new InstitutionId(UUID.randomUUID())) //
				.hauptstandort(hauptstandort) //
				.build();
	}

	private InstitutionStandort standort(final String name) {
		return InstitutionStandort.builder() //
				.id(new InstitutionStandortId(UUID.randomUUID())) //
				.name(name)//
				.build();
	}

	private AngebotId angebotId() {
		return new AngebotId(UUID.randomUUID());
	}

	private Angebot angebot(//
			final AngebotId angebotId, //
			final InstitutionStandort standort) {
		return Angebot.builder() //
				.id(angebotId) //
				.standort(standort) //
				.build();
	}
}
