package io.remedymatch.match.domain;

import io.remedymatch.angebot.domain.model.AngebotAnfrageId;
import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.bedarf.domain.model.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Validated
@Service
@Transactional
public class MatchService {

    private final UserContextService userService;
    private final AngebotAnfrageSucheService angebotAnfrageSucheService;
    private final BedarfAnfrageSucheService bedarfAnfrageSucheService;

    public List<Match> ladeBeteiligteMatches() {
        val angebotAnfragen = angebotAnfrageSucheService.findeAlleMachedAnfragenDerInstitution();
        val bedarfAnfragen = bedarfAnfrageSucheService.findeAlleMachedAnfragenDerInstitution();

        val matches = new ArrayList<Match>();
        matches.addAll(AnfrageToMatchMapper.mapAngebotAnfragen(angebotAnfragen));
        matches.addAll(AnfrageToMatchMapper.mapBedarfAnfragen(bedarfAnfragen));
        return matches;
    }

    public Match ladeMatch(UUID id) {
        val angebotAnfrage = angebotAnfrageSucheService.findAnfrage(new AngebotAnfrageId(id));

        if (angebotAnfrage.isPresent()) {
            return AnfrageToMatchMapper.map(angebotAnfrage.get());
        }

        val bedarfAnfrage = bedarfAnfrageSucheService.findAnfrage(new BedarfAnfrageId(id));

        if (bedarfAnfrage.isPresent()) {
            return AnfrageToMatchMapper.map(bedarfAnfrage.get());
        }

        throw new ObjectNotFoundException("Zu dieser AnfrageId existiert kein Match");
    }
}
