package io.remedymatch.angebot.domain;

import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.Angebot.AngebotBuilder;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotEntity.AngebotEntityBuilder;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

class AngebotEntityConverter {

	static Angebot convert(final AngebotEntity entity) {
		if (entity == null) {
			return null;
		}

		AngebotBuilder builder = Angebot.builder();

		builder.id(new AngebotId(entity.getId())) //
				.anzahl(entity.getAnzahl()) //
				.rest(entity.getRest()) //
				.artikel(entity.getArtikel()) //
				.institution(entity.getInstitution())//
				.standort(InstitutionStandortEntityConverter.convert(entity.getStandort()))
				.haltbarkeit(entity.getHaltbarkeit()) //
				.steril(entity.isSteril()) //
				.originalverpackt(entity.isOriginalverpackt()) //
				.medizinisch(entity.isMedizinisch()) //
				.kommentar(entity.getKommentar()) //
				.bedient(entity.isBedient());

		if (entity.getAnfragen() != null) {
			builder.anfragen(entity.getAnfragen().stream().map(AngebotAnfrageEntityConverter::convert)
					.collect(Collectors.toList()));
		}

		return builder.build();
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
				.artikel(angebot.getArtikel()) //
				.institution(angebot.getInstitution())//
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