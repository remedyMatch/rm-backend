package io.remedymatch.angebot.domain;

import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.institution.domain.InstitutionId;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.remedymatch.angebot.domain.AngebotEntityConverter.convert;

@Repository
@AllArgsConstructor
@Transactional
public class AngebotRepository {

    private final AngebotJpaRepository jpaRepository;

    public List<Angebot> getAlleNichtBedienteAngebote() {
        return jpaRepository.findAllByDeletedFalseAndBedientFalse().stream().map(AngebotEntityConverter::convert)
                .collect(Collectors.toList());
    }

    public List<Angebot> getAngeboteVonInstitution(final InstitutionId institutionId) {
        Assert.notNull(institutionId, "InstitutionId ist null");
        Assert.notNull(institutionId.getValue(), "InstitutionId ist null");

        return jpaRepository.findAllByDeletedFalseAndInstitution_Id(institutionId.getValue()).stream()//
                .map(AngebotEntityConverter::convert)//
                .collect(Collectors.toList());
    }

    public Optional<Angebot> get(final AngebotId angebotId) {
        Assert.notNull(angebotId, "AngebotId ist null");
        Assert.notNull(angebotId.getValue(), "AngebotId ist null");

        return jpaRepository.findById(angebotId.getValue()).map(AngebotEntityConverter::convert);
    }

    public boolean has(final AngebotId angebotId) {
        Assert.notNull(angebotId, "AngebotId ist null");
        Assert.notNull(angebotId.getValue(), "AngebotId ist null");

        return jpaRepository.existsById(angebotId.getValue());
    }

    public Angebot add(final Angebot angebot) {
        Assert.notNull(angebot, "Angebot ist null");

        return convert(jpaRepository.save(convert(angebot)));
    }

    public Angebot update(final Angebot angebot) {
        Assert.notNull(angebot, "Angebot ist null");

        return convert(jpaRepository.save(convert(angebot)));
    }

    public void delete(final AngebotId angebotId) {
        Assert.notNull(angebotId, "AngebotId ist null");
        Assert.notNull(angebotId.getValue(), "AngebotId ist null");

        val angebot = jpaRepository.findById(angebotId.getValue());
        angebot.get().setDeleted(true);

        jpaRepository.save(angebot.get());
    }
}
