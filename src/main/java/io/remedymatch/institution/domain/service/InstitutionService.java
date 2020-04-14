package io.remedymatch.institution.domain.service;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.domain.OperationNotAlloudException;
import io.remedymatch.geodaten.domain.StandortService;
import io.remedymatch.institution.domain.model.Institution;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.model.InstitutionUpdate;
import io.remedymatch.institution.domain.model.NeueInstitution;
import io.remedymatch.institution.domain.model.NeuesInstitutionStandort;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortJpaRepository;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Validated
@Service
@Transactional
@Slf4j
public class InstitutionService {

	private static final String EXCEPTION_MSG_UPDATE_OHNE_DATEN = "Keine Aenderungen in InstitutionUpdate gefunden";
	private static final String EXCEPTION_MSG_STANDORT_NICHT_IN_USER_INSTITUTION = "Standort nicht in UserInstitution gedfunden. (StandortId: $s)";

	private final InstitutionJpaRepository institutionRepository;
	private final InstitutionStandortJpaRepository institutionStandortRepository;
	private final StandortService standortService;
	private final UserService userService;

	public Institution institutionAnlegen(final @NotNull @Valid NeueInstitution neueInstitution) {
		
		log.debug("Lege neue Institution an: " + neueInstitution);
		
		val hauptstandort = standortErstellen(neueInstitution.getHauptstandort());
		return updateInstitution(InstitutionEntity.builder() //
				.name(neueInstitution.getName()) //
				.institutionKey(neueInstitution.getInstitutionKey()) //
				.typ(neueInstitution.getTyp()) //
				.hauptstandort(hauptstandort) //
				.standorte(Arrays.asList(hauptstandort)) //
				.build());
	}

	public Institution userInstitutionAktualisieren(final @NotNull @Valid InstitutionUpdate update) {
		
		log.debug("Aktualisiere User Institution: " + update);
		
		if (StringUtils.isBlank(update.getNeueName()) && update.getNeuesTyp() == null
				&& update.getNeuesHauptstandortId() == null) {
			throw new OperationNotAlloudException(EXCEPTION_MSG_UPDATE_OHNE_DATEN);
		}

		val userInstitution = getUserInstitution();
		if (StringUtils.isNotBlank(update.getNeueName())) {
			userInstitution.setName(update.getNeueName());
		}
		if (update.getNeuesTyp() != null) {
			userInstitution.setTyp(update.getNeuesTyp());
		}
		if (update.getNeuesHauptstandortId() != null) {
			userInstitution.setHauptstandort(getStandort(userInstitution, update.getNeuesHauptstandortId()));
		}

		return updateInstitution(userInstitution);
	}

	public Institution userInstitutionHauptstandortHinzufuegen(
			final @NotNull @Valid NeuesInstitutionStandort neuesStandort) {
		
		log.debug("Setze neues Hauptstandort in User Institution: " + neuesStandort);
		
		val userInstitution = getUserInstitution();
		val standort = standortErstellen(neuesStandort);
		userInstitution.addStandort(standort);
		userInstitution.setHauptstandort(standort);

		return updateInstitution(userInstitution);
	}

	public Institution userInstitutionStandortHinzufuegen(
			final @NotNull @Valid NeuesInstitutionStandort neuesStandort) {

		log.debug("Setze neues Standort in User Institution: " + neuesStandort);
		
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

	InstitutionStandortEntity getStandort(//
			final @NotNull @Valid InstitutionEntity institution, //
			@NotNull @Valid final InstitutionStandortId standortId) {
		return institution.findStandort(standortId.getValue()).orElseThrow(() -> new ObjectNotFoundException(String
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

	InstitutionStandortEntity mitGeodatenErweitern(final InstitutionStandortEntity standort) {
		val addresseFuerGeocoding = formatAdresse(standort);
		log.info("Suche Geodaten für: " + addresseFuerGeocoding);
		var longlatList = standortService.findePointsByAdressString(addresseFuerGeocoding);

		if (longlatList == null || longlatList.size() == 0) {
			throw new ObjectNotFoundException("Die Adresse konnte nicht aufgelöst werden");
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
