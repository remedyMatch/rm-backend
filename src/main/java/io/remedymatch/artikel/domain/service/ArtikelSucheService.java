package io.remedymatch.artikel.domain.service;

import static io.remedymatch.artikel.domain.service.ArtikelEntityConverter.convertArtikel;
import static io.remedymatch.artikel.domain.service.ArtikelKategorieEntityConverter.convertKategorien;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import io.remedymatch.artikel.domain.model.Artikel;
import io.remedymatch.artikel.domain.model.ArtikelId;
import io.remedymatch.artikel.domain.model.ArtikelKategorie;
import io.remedymatch.artikel.domain.model.ArtikelVariante;
import io.remedymatch.artikel.domain.model.ArtikelVarianteId;
import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelVarianteJpaRepository;
import io.remedymatch.domain.ObjectNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Validated
public class ArtikelSucheService {

	private static final String EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN = "Artikel mit diesem Id nicht gefunden. (Id: %s)";
	private static final String EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN = "Artikel Variante mit diesem Id nicht gefunden. (Id: %s)";

	private final ArtikelKategorieJpaRepository artikelKategorieRepository;
	private final ArtikelJpaRepository artikelRepository;
	private final ArtikelVarianteJpaRepository artikelVarianteRepository;

	@Transactional(readOnly = true)
	public List<ArtikelKategorie> findAlleKategorien() {
		return convertKategorien(artikelKategorieRepository.findAll());
	}

	@Transactional(readOnly = true)
	public List<Artikel> findAlleArtikel() {
		return convertArtikel(artikelRepository.findAll());
	}

	@Transactional(readOnly = true)
	public List<Artikel> findByNameLike(final String nameLike) {
		if (StringUtils.isBlank(nameLike)) {
			return findAlleArtikel();
		}
		return convertArtikel(artikelRepository.findByNameContainingIgnoreCase(nameLike));
	}

	/**
	 * In Vergleich zur findArtikel wirft eine ObjectNotFoundException wenn nicht
	 * gefunden
	 * 
	 * @param artikelId ArtikelId
	 * @return Artikel
	 * @throws ObjectNotFoundException
	 */
	@Transactional(readOnly = true)
	public Artikel getArtikelOrElseThrow(final @NotNull @Valid ArtikelId artikelId) throws ObjectNotFoundException {
		return findArtikel(artikelId).orElseThrow(() -> new ObjectNotFoundException(
				String.format(EXCEPTION_MSG_ARTIKEL_NICHT_GEFUNDEN, artikelId.getValue())));
	}

	@Transactional(readOnly = true)
	public Optional<Artikel> findArtikel(final @NotNull @Valid ArtikelId artikelId) {
		Assert.notNull(artikelId, "ArtikelId ist null.");

		return artikelRepository.findById(artikelId.getValue()).map(ArtikelEntityConverter::convertArtikel);
	}

	/**
	 * In Vergleich zur findArtikelVariante wirft eine ObjectNotFoundException wenn
	 * nicht gefunden
	 * 
	 * @param artikelVarianteId ArtikelVarianteId
	 * @return ArtikelVariante
	 * @throws ObjectNotFoundException
	 */
	@Transactional(readOnly = true)
	public ArtikelVariante getArtikelVarianteOrElseThrow(final @NotNull @Valid ArtikelVarianteId artikelVarianteId)
			throws ObjectNotFoundException {
		return findArtikelVariante(artikelVarianteId).orElseThrow(() -> new ObjectNotFoundException(
				String.format(EXCEPTION_MSG_ARTIKEL_VARIANTE_NICHT_GEFUNDEN, artikelVarianteId.getValue())));
	}

	@Transactional(readOnly = true)
	public Optional<ArtikelVariante> findArtikelVariante(final @NotNull @Valid ArtikelVarianteId artikelVarianteId) {
		Assert.notNull(artikelVarianteId, "ArtikelVarianteId ist null.");

		return artikelVarianteRepository.findById(artikelVarianteId.getValue())
				.map(ArtikelEntityConverter::convertVariante);
	}
}
