package io.remedymatch.institution.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.geodaten.api.StandortService;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Validated
@Service
public class InstitutionService {
	private static final String EXCEPTION_MSG_INSTITUTION_NICHT_GEFUNDEN = "Institution fuer diese Id nicht gefunden: %s";
	private static final String EXCEPTION_MSG_INSTITUTION_NICHT_VON_USER_INSTITUTION = "Institution gehoert dem angemeldeten Benutzer.";

	private final InstitutionRepository institutionRepository;
	private final InstitutionStandortRepository institutionStandortRepository;
	private final StandortService standortService;
	private final UserService userService;
	private final AngebotAnfrageSucheService angebotAnfrageSucheService;
	private final BedarfAnfrageSucheService bedarfAnfrageSucheService;
	private final GeoCalcService geoCalcService;

	public Institution userInstitutionLaden() {
		return userService.getContextInstitution();
	}

	public Institution userInstitutionAktualisieren(//
			final @NotNull @Valid InstitutionId institutionId, //
			final @NotBlank String name, //
			final @NotNull InstitutionTyp typ) throws ObjectNotFoundException, NotUserInstitutionObjectException {
		val institution = institutionRepository.get(institutionId);
		if (!institution.isPresent()) {
			throw new ObjectNotFoundException(String.format(EXCEPTION_MSG_INSTITUTION_NICHT_GEFUNDEN, institutionId));
		}

		if (!userService.isUserContextInstitution(institutionId)) {
			throw new NotUserInstitutionObjectException(EXCEPTION_MSG_INSTITUTION_NICHT_VON_USER_INSTITUTION);
		}

		val institutioUpdate = institution.get();
		institutioUpdate.setName(name);
		institutioUpdate.setTyp(typ);
		return institutionRepository.update(institutioUpdate);
	}

	public Institution userInstitutionHauptstandortAktualisieren(//
			final @NotNull @Valid InstitutionStandort standort) {
		val aktualisierteStandort = standortSpeichern(standort);
		val userInstitution = userService.getContextInstitution();
		userInstitution.setHauptstandort(aktualisierteStandort);
		return institutionRepository.update(userInstitution);
	}

	public Institution userInstitutionStandortHinzufuegen(//
			final @NotNull @Valid InstitutionStandort standort) {
		val aktualisierteStandort = standortSpeichern(standort);
		val userInstitution = userService.getContextInstitution();
		userInstitution.getStandorte().add(aktualisierteStandort);
		return institutionRepository.update(userInstitution);
	}

	public Institution userInstitutionStandortEntfernen(//
			final @NotNull @Valid InstitutionStandortId standortId) {
		val userInstitution = userService.getContextInstitution();
		var standort = userInstitution.getStandorte().stream().filter(s -> s.getId().equals(standortId)).findFirst();
		if (standort.isEmpty()) {
			throw new IllegalArgumentException("Standort kann nicht gelöscht werden, da dieser nicht vorhanden ist.");
		}

		// TODO prüfen ob dieser Standort in Anfrage / Angebot / Bedarf verwendet wird.
		// Wenn ja -> kann er nicht gelöscht werden

		userInstitution.getStandorte().remove(standort.get());
		val inst = institutionRepository.update(userInstitution);
		institutionStandortRepository.delete(standort.get().getId());
		return inst;
	}

	public List<Anfrage> getGestellteUserInstitutionAnfragen() {
		val userInstitutionId = userService.getContextInstitution().getId();

		val angebotAnfragen = angebotAnfrageSucheService.findAlleAnfragenDerInstitution(userInstitutionId);
		val bedarfAnfragen = bedarfAnfrageSucheService.findAlleAnfragenDerInstitution(userInstitutionId);
		val anfragen = angebotAnfragen.stream().map(AnfrageConverter::convert).collect(Collectors.toList());
		anfragen.addAll(bedarfAnfragen.stream().map(AnfrageConverter::convert).collect(Collectors.toList()));

		return mitEntfernung(anfragen);
	}

	public List<Anfrage> getErhalteneUserInstitutionAnfragen() {
		val userInstitutionId = userService.getContextInstitution().getId();

		val angebotAnfragen = angebotAnfrageSucheService.findAlleAnfragenDerAngebotInstitution(userInstitutionId);
		val bedarfAnfragen = bedarfAnfrageSucheService.findAlleAnfragenDerBedarfInstitution(userInstitutionId);
		val anfragen = angebotAnfragen.stream().map(AnfrageConverter::convert).collect(Collectors.toList());
		anfragen.addAll(bedarfAnfragen.stream().map(AnfrageConverter::convert).collect(Collectors.toList()));

		return mitEntfernung(anfragen);
	}

	/* help methods */

	private InstitutionStandort standortSpeichern(final InstitutionStandort standort) {
		var longlatList = standortService.findePointsByAdressString(standort.getAdresse());

		if (longlatList == null || longlatList.size() == 0) {
			throw new IllegalArgumentException("Die Adresse konnte nicht aufgelöst werden");
		}

		standort.setLatitude(BigDecimal.valueOf(longlatList.get(0).getLatitude()));
		standort.setLongitude(BigDecimal.valueOf(longlatList.get(0).getLongitude()));

		return institutionStandortRepository.update(standort);
	}

	private List<Anfrage> mitEntfernung(final List<Anfrage> anfragen) {
		anfragen.forEach(anfrage -> anfrage.setEntfernung(geoCalcService.berechneDistanzInKilometer(//
				anfrage.getStandortVon(), //
				anfrage.getStandortAn())));

		return anfragen;
	}
}
