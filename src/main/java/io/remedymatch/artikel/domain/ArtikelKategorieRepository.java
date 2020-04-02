package io.remedymatch.artikel.domain;

import static io.remedymatch.artikel.domain.ArtikelKategorieEntityConverter.convert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import io.remedymatch.artikel.infrastructure.ArtikelKategorieJpaRepository;

@Repository
public class ArtikelKategorieRepository {
	@Autowired
	private ArtikelKategorieJpaRepository jpaRepository;
	
	public List<ArtikelKategorie> getAlle() {
		return jpaRepository.findAll().stream().map(ArtikelKategorieEntityConverter::convert).collect(Collectors.toList());
	}
	
	public List<ArtikelKategorie> findByNameLike(final String nameLike) {
		Assert.isTrue(StringUtils.isNotBlank(nameLike), "NameLike ist blank");

		return jpaRepository.findByNameLike(nameLike).stream().map(ArtikelKategorieEntityConverter::convert).collect(Collectors.toList());
	}
	
	public Optional<ArtikelKategorie> findByName(final String name) {
		Assert.isTrue(StringUtils.isNotBlank(name), "Name ist blank");

		return jpaRepository.findByName(name).map(ArtikelKategorieEntityConverter::convert);
	}
	
	public Optional<ArtikelKategorie> get(final ArtikelKategorieId artikelKategorieId) {
		Assert.notNull(artikelKategorieId, "ArtikelKategorieId ist null");
		Assert.notNull(artikelKategorieId.getValue(), "ArtikelKategorieId ist null");

		return jpaRepository.findById(artikelKategorieId.getValue()).map(ArtikelKategorieEntityConverter::convert);
	}
	
	public ArtikelKategorie add(final ArtikelKategorie artikelKategorie) {
		Assert.notNull(artikelKategorie, "ArtikelKategorie ist null");

		return convert(jpaRepository.save(convert(artikelKategorie)));
	}
}
