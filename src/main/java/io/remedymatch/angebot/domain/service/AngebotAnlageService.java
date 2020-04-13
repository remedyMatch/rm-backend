package io.remedymatch.angebot.domain.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.NeuesAngebot;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.artikel.domain.service.ArtikelSucheService;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteEntity;
import io.remedymatch.domain.NotUserInstitutionObjectException;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.geodaten.geocoding.domain.GeoCalcService;
import io.remedymatch.institution.domain.model.InstitutionStandortId;
import io.remedymatch.institution.domain.service.InstitutionEntityConverter;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@AllArgsConstructor
@Service
@Validated
@Transactional
public class AngebotAnlageService {
	private static final String EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN = "ArtikelVariante mit diesem Id nicht gefunden. (Id: %s)";

	private static final String EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION = "Standort gehoert nicht der Institution des angemeldetes Benutzers. (Id: %s)";

	private final AngebotJpaRepository angebotRepository;

	private final UserService userService;
	private final ArtikelSucheService artikelSucheService;
	private final GeoCalcService geoCalcService;

	@Transactional
	public Angebot neueAngebotEinstellen(final @NotNull @Valid NeuesAngebot neuesAngebot) {
		val userInstitution = getUserInstitution();

		return mitEntfernung(angebotRepository.save(AngebotEntity.builder() //
				.anzahl(neuesAngebot.getAnzahl()) //
				.rest(neuesAngebot.getAnzahl()) //
				.artikelVariante(getArtikelVariante(neuesAngebot.getArtikelVarianteId())) //
				.institution(userInstitution) //
				.standort(getUserInstitutionStandort(userInstitution, neuesAngebot.getStandortId())) //
				.haltbarkeit(neuesAngebot.getHaltbarkeit()) //
				.steril(neuesAngebot.isSteril()) //
				.originalverpackt(neuesAngebot.isOriginalverpackt()) //
				.medizinisch(neuesAngebot.isMedizinisch()) //
				.kommentar(neuesAngebot.getKommentar()) //
				.bedient(false) //
				.build()));
	}

	private Angebot mitEntfernung(final @NotNull AngebotEntity angebot) {
		val convertedAngebot = AngebotEntityConverter.convertAngebot(angebot);
		convertedAngebot.setEntfernung(geoCalcService.berechneUserDistanzInKilometer(convertedAngebot.getStandort()));
		return convertedAngebot;
	}

	ArtikelVarianteEntity getArtikelVariante(//
			final @NotNull @Valid ArtikelVarianteId artikelVarianteId) {
		Assert.notNull(artikelVarianteId, "ArtikelVarianteId sind null.");

		return artikelSucheService.findArtikelVariante(artikelVarianteId).map(ArtikelEntityConverter::convertVariante) //
				.orElseThrow(() -> new ObjectNotFoundException(
						String.format(EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN, artikelVarianteId.getValue())));
	}

	private InstitutionEntity getUserInstitution() {
		return InstitutionEntityConverter.convertInstitution(userService.getContextInstitution());
	}

	InstitutionStandortEntity getUserInstitutionStandort( //
			final @NotNull InstitutionEntity userInstitution, //
			final @NotNull @Valid InstitutionStandortId institutionStandortId) {
		Assert.notNull(userInstitution, "InstitutionEntity ist null.");
		Assert.notNull(institutionStandortId, "InstitutionStandortId ist null.");

		return userInstitution.findStandort(institutionStandortId.getValue()) //
				.orElseThrow(() -> new NotUserInstitutionObjectException(String
						.format(EXCEPTION_MSG_STANDORT_NICHT_VON_USER_INSTITUTION, institutionStandortId.getValue())));
	}
}
