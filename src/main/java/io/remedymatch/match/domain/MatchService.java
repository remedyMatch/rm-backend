package io.remedymatch.match.domain;

import io.remedymatch.angebot.domain.service.AngebotAnfrageSucheService;
import io.remedymatch.bedarf.domain.service.BedarfAnfrageSucheService;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

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
}
