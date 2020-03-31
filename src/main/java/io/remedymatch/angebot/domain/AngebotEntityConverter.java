package io.remedymatch.angebot.domain;

import java.util.stream.Collectors;

import io.remedymatch.angebot.domain.Angebot.AngebotBuilder;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotEntity.AngebotEntityBuilder;

class AngebotEntityConverter {

	static Angebot convert(AngebotEntity entity) {
		if (entity == null) {
			return null;
		}

		AngebotBuilder builder = Angebot.builder();

		builder.id(entity.getId()) //
				.anzahl(entity.getAnzahl()) //
				.rest(entity.getRest()) //
				.artikel(entity.getArtikel()) //
				.institution(entity.getInstitution()).standort(entity.getStandort())
				.haltbarkeit(entity.getHaltbarkeit()) //
				.steril(entity.isSteril()) //
				.originalverpackt(entity.isOriginalverpackt()) //
				.medizinisch(entity.isMedizinisch()) //
				.kommentar(entity.getKommentar()) //
				.bedient(entity.isBedient());
		
		if (entity.getAnfragen() != null)
		{
			builder.anfragen(entity.getAnfragen().stream().map(AngebotAnfrageEntityConverter::convert)
					.collect(Collectors.toList()));
		}
		
		return builder.build();
	}

	static AngebotEntity convert(Angebot angebot) {
		if (angebot == null) {
			return null;
		}

		AngebotEntityBuilder builder = AngebotEntity.builder();
		builder //
				.id(angebot.getId()) //
				.anzahl(angebot.getAnzahl()) //
				.rest(angebot.getRest()) //
				.artikel(angebot.getArtikel()) //
				.institution(angebot.getInstitution()).standort(angebot.getStandort())
				.haltbarkeit(angebot.getHaltbarkeit()) //
				.steril(angebot.isSteril()) //
				.originalverpackt(angebot.isOriginalverpackt()) //
				.medizinisch(angebot.isMedizinisch()) //
				.kommentar(angebot.getKommentar()) //
				.bedient(angebot.isBedient());;

		if (angebot.getAnfragen() != null)
		{
			builder.anfragen(angebot.getAnfragen().stream().map(AngebotAnfrageEntityConverter::convert)
					.collect(Collectors.toList()));
		}
		
		return builder.build();
}
}