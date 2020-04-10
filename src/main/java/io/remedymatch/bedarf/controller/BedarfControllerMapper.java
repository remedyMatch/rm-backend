package io.remedymatch.bedarf.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.bedarf.domain.model.Bedarf;
import io.remedymatch.bedarf.domain.model.BedarfAnfrage;
import io.remedymatch.bedarf.domain.model.BedarfId;
import io.remedymatch.bedarf.domain.model.NeuesBedarf;
import io.remedymatch.institution.api.InstitutionMapper;
import io.remedymatch.institution.api.InstitutionStandortMapper;
import io.remedymatch.institution.domain.InstitutionStandortId;

public class BedarfControllerMapper {

	static BedarfAnfrageRO mapToAnfrageRO(final BedarfAnfrage bedarfAnfrage) {
		return BedarfAnfrageRO.builder() //
				.id(bedarfAnfrage.getId().getValue()) //
				.bedarf(mapToBedarfRO(bedarfAnfrage.getBedarf())) //
				.institution(InstitutionMapper.mapToDTO(bedarfAnfrage.getInstitution())) //
				.standort(InstitutionStandortMapper.mapToDTO(bedarfAnfrage.getStandort())) //
				.anzahl(bedarfAnfrage.getAnzahl()) //
				.kommentar(bedarfAnfrage.getKommentar()) //
				.prozessInstanzId(bedarfAnfrage.getProzessInstanzId()) //
				.status(bedarfAnfrage.getStatus()) //
				.build();
	}

	static List<BedarfRO> mapToBedarfeRO(final List<Bedarf> bedarfe) {
		return bedarfe.stream().map(BedarfControllerMapper::mapToBedarfRO).collect(Collectors.toList());
	}

	static BedarfRO mapToBedarfRO(final Bedarf bedarf) {
		return BedarfRO.builder() //
				.id(bedarf.getId().getValue()) //
				.artikelId(bedarf.getArtikel().getId().getValue()) //
				.artikelVarianteId(bedarf.getArtikelVariante().getId().getValue()) //
				.anzahl(bedarf.getAnzahl()) //
				.rest(bedarf.getRest()) //
				.institutionId(bedarf.getInstitution().getId().getValue()) //
				.standort(InstitutionStandortMapper.mapToDTO(bedarf.getStandort())) //
				.steril(bedarf.isSteril()) //
				.medizinisch(bedarf.isMedizinisch()) //
				.kommentar(bedarf.getKommentar()) //
				.entfernung(bedarf.getEntfernung()) //
				.build();
	}

	static NeuesBedarf mapToNeuesBedarf(final NeuesBedarfRequest neuesBedarfRequest) {
		return NeuesBedarf.builder() //
				.artikelId(neuesBedarfRequest.getArtikelId() != null ? new ArtikelId(neuesBedarfRequest.getArtikelId())
						: null) //
				.artikelVarianteId(neuesBedarfRequest.getArtikelVarianteId() != null
						? new ArtikelVarianteId(neuesBedarfRequest.getArtikelVarianteId())
						: null) //
				.anzahl(neuesBedarfRequest.getAnzahl()) //
				.standortId(new InstitutionStandortId(neuesBedarfRequest.getStandortId())) //
				.steril(neuesBedarfRequest.isSteril()) //
				.medizinisch(neuesBedarfRequest.isMedizinisch()) //
				.kommentar(neuesBedarfRequest.getKommentar()) //
				.build();
	}

	static BedarfId maptToBedarfId(final String bedarfId) {
		return maptToBedarfId(UUID.fromString(bedarfId));
	}

	static BedarfId maptToBedarfId(final UUID bedarfId) {
		return new BedarfId(bedarfId);
	}
}