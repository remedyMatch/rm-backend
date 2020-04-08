package io.remedymatch.angebot.domain.service;

import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.model.Angebot;
import io.remedymatch.angebot.domain.model.AngebotId;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotEntity.AngebotEntityBuilder;
import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

class AngebotEntityConverter {

	static Angebot convert(final AngebotEntity entity) {
		if (entity == null) {
			return null;
		}
		
		return Angebot.builder() //
				.id(new AngebotId(entity.getId())) //
				.anzahl(entity.getAnzahl()) //
				.rest(entity.getRest()) //
				.artikelVariante(ArtikelEntityConverter.convertVariante(entity.getArtikelVariante())) //
				.institution(InstitutionEntityConverter.convert(entity.getInstitution()))//
				.standort(InstitutionStandortEntityConverter.convert(entity.getStandort()))
				.haltbarkeit(entity.getHaltbarkeit()) //
				.steril(entity.isSteril()) //
				.originalverpackt(entity.isOriginalverpackt()) //
				.medizinisch(entity.isMedizinisch()) //
				.kommentar(entity.getKommentar()) //
				.bedient(entity.isBedient()) //
				.anfragen(AngebotAnfrageEntityConverter.convertAnfragen(entity.getAnfragen())) //
				.build();
	}

	static AngebotEntity convert(final Angebot angebot) {
		if (angebot == null) {
			return null;
		}

		AngebotEntityBuilder builder = AngebotEntity.builder();
		if (angebot.getId() != null) {
			builder.id(angebot.getId().getValue());
		}

		builder.anzahl(angebot.getAnzahl()) //
				.rest(angebot.getRest()) //
				.artikelVariante(ArtikelEntityConverter.convertVariante(angebot.getArtikelVariante())) //
				.institution(InstitutionEntityConverter.convert(angebot.getInstitution()))//
				.standort(InstitutionStandortEntityConverter.convert(angebot.getStandort()))
				.haltbarkeit(angebot.getHaltbarkeit()) //
				.steril(angebot.isSteril()) //
				.originalverpackt(angebot.isOriginalverpackt()) //
				.medizinisch(angebot.isMedizinisch()) //
				.kommentar(angebot.getKommentar()) //
				.bedient(angebot.isBedient());
		;

		if (angebot.getAnfragen() != null) {
			builder.anfragen(angebot.getAnfragen().stream().map(AngebotAnfrageEntityConverter::convert)
					.collect(Collectors.toList()));
		}

		return builder.build();
	}
}