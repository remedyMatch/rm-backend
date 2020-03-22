package io.remedymatch.artikel.domain;

import io.remedymatch.artikel.api.ArtikelDTO;
import io.remedymatch.artikel.api.ArtikelMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@AllArgsConstructor
@Repository
public class ArtikleRepository {
    private final ArtikelJpaRepository jpaRepository;
    private final ArtikelKategorieRepository artikelKategorieRepository;

    public List<ArtikelDTO> search(String nameLike) {
        if (nameLike != null && !nameLike.isBlank()) {
            return jpaRepository.findByNameLike("%" + nameLike + "%").stream().map(ArtikelMapper::getArticleDTO).collect(Collectors.toList());
        } else {
            return jpaRepository.findAll().stream().map(ArtikelMapper::getArticleDTO).collect(Collectors.toList());
        }
    }

    public ArtikelDTO get(UUID articleId) {
        return ArtikelMapper.getArticleDTO(jpaRepository.findById(articleId).orElseThrow());
    }

    public ArtikelDTO add(ArtikelDTO artikel) {
        var toSave = ArtikelMapper.getArticleEntity(artikel);

        if (artikel.getArtikelKategorie() != null) {
            toSave.setArtikelKategorie(artikelKategorieRepository.findById(artikel.getArtikelKategorie().getId()).orElseThrow());
        }
        return ArtikelMapper.getArticleDTO(jpaRepository.save(toSave));
    }

    public ArtikelDTO update(ArtikelDTO artikelDTO) {
        var entity = jpaRepository.findById(artikelDTO.getId()).orElseThrow();

        ArtikelKategorieEntity artikelKategorieEntity;
        if (artikelDTO.getArtikelKategorie() != null && artikelDTO.getArtikelKategorie().getId() != null) {
            artikelKategorieEntity = artikelKategorieRepository.findById(artikelDTO.getArtikelKategorie().getId()).orElseThrow();
        } else {
            artikelKategorieEntity = null;
        }
        entity.setArtikelKategorie(artikelKategorieEntity);
        entity.setBeschreibung(artikelDTO.getBeschreibung());
        entity.setEan(artikelDTO.getEan());
        entity.setHersteller(artikelDTO.getHersteller());
        entity.setName(artikelDTO.getName());
        return ArtikelMapper.getArticleDTO(jpaRepository.save(entity));
    }
}
