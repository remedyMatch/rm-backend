package io.remedymatch.bedarf.domain;

import java.util.stream.Collectors;

import io.remedymatch.artikel.domain.ArtikelEntityConverter;
import io.remedymatch.bedarf.domain.Bedarf.BedarfBuilder;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfEntity.BedarfEntityBuilder;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

class BedarfEntityConverter {

	static Bedarf convert(final BedarfEntity entity) {
		if (entity == null) {
			return null;
		}

		BedarfBuilder builder = Bedarf.builder();

		builder.id(new BedarfId(entity.getId())) //
				.anzahl(entity.getAnzahl()) //
				.rest(entity.getRest()) //
				.artikel(ArtikelEntityConverter.convert(entity.getArtikel())) //
				.institution(InstitutionEntityConverter.convert(entity.getInstitution()))//
				.standort(InstitutionStandortEntityConverter.convert(entity.getStandort()))
				.steril(entity.isSteril()) //
				.originalverpackt(entity.isOriginalverpackt()) //
				.medizinisch(entity.isMedizinisch()) //
				.kommentar(entity.getKommentar()) //
				.bedient(entity.isBedient());

		if (entity.getAnfragen() != null) {
			builder.anfragen(entity.getAnfragen().stream().map(BedarfAnfrageEntityConverter::convert)
					.collect(Collectors.toList()));
		}

		return builder.build();
	}

	static BedarfEntity convert(final Bedarf bedarf) {
		if (bedarf == null) {
			return null;
		}

		BedarfEntityBuilder builder = BedarfEntity.builder();
		if (bedarf.getId() != null) {
			builder.id(bedarf.getId().getValue());
		}

		builder.anzahl(bedarf.getAnzahl()) //
				.rest(bedarf.getRest()) //
				.artikel(ArtikelEntityConverter.convert(bedarf.getArtikel())) //
				.institution(InstitutionEntityConverter.convert(bedarf.getInstitution()))//
				.standort(InstitutionStandortEntityConverter.convert(bedarf.getStandort()))
				.steril(bedarf.isSteril()) //
				.originalverpackt(bedarf.isOriginalverpackt()) //
				.medizinisch(bedarf.isMedizinisch()) //
				.kommentar(bedarf.getKommentar()) //
				.bedient(bedarf.isBedient());
		;

		if (bedarf.getAnfragen() != null) {
			builder.anfragen(bedarf.getAnfragen().stream().map(BedarfAnfrageEntityConverter::convert)
					.collect(Collectors.toList()));
		}

		return builder.build();
	}
}