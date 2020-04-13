package io.remedymatch.institution.domain.service;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.geodaten.domain.StandortService;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.model.InstitutionTyp;
import io.remedymatch.institution.domain.model.NeuesInstitutionStandort;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortJpaRepository;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
@Transactional
public class InstitutionService {

	private static final String EXCEPTION_MSG_STANDORT_NICHT_IN_USER_INSTITUTION = "Standort nicht in UserInstitution gedfunden. (StandortId: $s)";

	private final InstitutionJpaRepository institutionRepository;
	private final InstitutionStandortJpaRepository institutionStandortRepository;
	private final StandortService standortService;
	private final UserService userService;

	public Institution userInstitutionAktualisieren(//
			final String neueName, //
			final InstitutionTyp neuesTyp, //
			final InstitutionStandortId neuesHauptstandortId) {
		val userInstitution = getUserInstitution();
		if (StringUtils.isNotBlank(neueName)) {
			userInstitution.setName(neueName);
		}
		if (neuesTyp != null) {
			userInstitution.setTyp(neuesTyp);
		}
		// FIXME Hauptstandort noch...

		return updateInstitution(userInstitution);
	}

	public Institution userInstitutionHauptstandortAktualisieren(
			final @NotNull @Valid InstitutionStandortId standortId) {
		val userInstitution = getUserInstitution();
		userInstitution.setHauptstandort(getStandort(userInstitution, standortId));

		return updateInstitution(userInstitution);
	}

	public Institution userInstitutionHauptstandortHinzufuegen(
			final @NotNull @Valid NeuesInstitutionStandort neuesStandort) {
		val userInstitution = getUserInstitution();
		val standort = standortErstellen(neuesStandort);
		userInstitution.addStandort(standort);
		userInstitution.setHauptstandort(standort);

		return updateInstitution(userInstitution);
	}

	public Institution userInstitutionStandortHinzufuegen(
			final @NotNull @Valid NeuesInstitutionStandort neuesStandort) {
		val userInstitution = getUserInstitution();
		userInstitution.getStandorte().add(standortErstellen(neuesStandort));

		return updateInstitution(userInstitution);
	}

	/* help methods */

	private Institution updateInstitution(final @NotNull @Valid InstitutionEntity institutionEntity) {
		return InstitutionEntityConverter.convertInstitution(institutionRepository.save(institutionEntity));
	}

	private InstitutionEntity getUserInstitution() {
		return InstitutionEntityConverter.convertInstitution(userService.getContextInstitution());
	}

	private InstitutionStandortEntity getStandort(//
			final @NotNull @Valid InstitutionEntity institution, //
			@NotNull @Valid final InstitutionStandortId standortId) {
		return institution.findStandort(standortId.getValue()).orElseThrow(() -> new IllegalArgumentException(String
				.format(EXCEPTION_MSG_STANDORT_NICHT_IN_USER_INSTITUTION, institution.getId(), standortId.getValue())));
	}

	private InstitutionStandortEntity standortErstellen(final @NotNull @Valid NeuesInstitutionStandort neuesStandort) {
		return institutionStandortRepository.save(mitGeodatenErweitern(InstitutionStandortEntity.builder() //
				.name(neuesStandort.getName()) //
				.strasse(neuesStandort.getStrasse()) //
				.hausnummer(neuesStandort.getHausnummer()) //
				.plz(neuesStandort.getPlz()) //
				.ort(neuesStandort.getOrt()) //
				.land(neuesStandort.getLand()) //
				.build()));
	}

	private InstitutionStandortEntity mitGeodatenErweitern(final InstitutionStandortEntity standort) {
		var longlatList = standortService.findePointsByAdressString(formatAdresse(standort));

		if (longlatList == null || longlatList.size() == 0) {
			throw new IllegalArgumentException("Die Adresse konnte nicht aufgelöst werden");
		}

		standort.setLatitude(BigDecimal.valueOf(longlatList.get(0).getLatitude()));
		standort.setLongitude(BigDecimal.valueOf(longlatList.get(0).getLongitude()));

		return standort;
	}

	private String formatAdresse(final InstitutionStandortEntity standort) {
		return String.format("%s %s, %s %s, %s", //
				standort.getStrasse(), //
				standort.getHausnummer(), //
				standort.getPlz(), //
				standort.getOrt(), //
				standort.getLand());
	}
}
