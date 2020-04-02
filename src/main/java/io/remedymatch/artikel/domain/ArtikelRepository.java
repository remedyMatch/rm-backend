package io.remedymatch.artikel.domain;

import static io.remedymatch.artikel.domain.ArtikelEntityConverter.convert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class ArtikelRepository {
	private final ArtikelJpaRepository jpaRepository;

	public List<Artikel> getAlle() {
		return jpaRepository.findAll().stream().map(ArtikelEntityConverter::convert).collect(Collectors.toList());
	}

	public List<Artikel> getArtikelVonKategorie(final ArtikelKategorieId artikelKategorieId) {
		Assert.notNull(artikelKategorieId, "ArtikelKategorieId ist null");
		Assert.notNull(artikelKategorieId.getValue(), "ArtikelKategorieId ist null");

		return jpaRepository.findAllByArtikelKategorie_Id(artikelKategorieId.getValue()).stream()//
				.map(ArtikelEntityConverter::convert)//
				.collect(Collectors.toList());
	}

	public List<Artikel> findByNameLike(final String nameLike) {
		if (StringUtils.isEmpty(nameLike)) {
			return jpaRepository.findAll().stream().map(ArtikelEntityConverter::convert).collect(Collectors.toList());
		}
		return jpaRepository.findByNameLike(nameLike).stream().map(ArtikelEntityConverter::convert)
				.collect(Collectors.toList());
	}

	public Optional<Artikel> get(final ArtikelId artikelId) {
		Assert.notNull(artikelId, "ArtikelId ist null");
		Assert.notNull(artikelId.getValue(), "ArtikelId ist null");

		return jpaRepository.findById(artikelId.getValue()).map(ArtikelEntityConverter::convert);
	}

	public Artikel add(final Artikel artikel) {
		Assert.notNull(artikel, "Artikel ist null");

		return convert(jpaRepository.save(convert(artikel)));
	}
}
