package io.remedymatch.bedarf.domain.service;

import java.util.stream.Collectors;

import io.remedymatch.artikel.domain.service.ArtikelEntityConverter;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.infrastructure.BedarfEntity;
import io.remedymatch.bedarf.infrastructure.BedarfEntity.BedarfEntityBuilder;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.institution.domain.InstitutionStandortEntityConverter;

class BedarfEntityConverter {

	static Bedarf convert(final BedarfEntity entity) {
		if (entity == null) {
			return null;
		}
		
		return Bedarf.builder() //
				.id(new BedarfId(entity.getId())) //
				.anzahl(entity.getAnzahl()) //
				.rest(entity.getRest()) //
				.artikel(ArtikelEntityConverter.convertArtikel(entity.getArtikel())) //
				.artikelVariante(ArtikelEntityConverter.convertVariante(entity.getArtikelVariante())) //
				.institution(InstitutionEntityConverter.convert(entity.getInstitution()))//
				.standort(InstitutionStandortEntityConverter.convert(entity.getStandort()))
				.steril(entity.isSteril()) //
				.medizinisch(entity.isMedizinisch()) //
				.kommentar(entity.getKommentar()) //
				.bedient(entity.isBedient()) //
				.anfragen(BedarfAnfrageEntityConverter.convertAnfragen(entity.getAnfragen())) //
				.build();
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
				.artikel(ArtikelEntityConverter.convertArtikel(bedarf.getArtikel())) //
				.artikelVariante(ArtikelEntityConverter.convertVariante(bedarf.getArtikelVariante())) //
				.institution(InstitutionEntityConverter.convert(bedarf.getInstitution()))//
				.standort(InstitutionStandortEntityConverter.convert(bedarf.getStandort())).steril(bedarf.isSteril()) //
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