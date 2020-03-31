package io.remedymatch.institution.api;

import io.remedymatch.angebot.api.AngebotDTO;
import io.remedymatch.angebot.api.AngebotMapper;
import io.remedymatch.angebot.domain.anfrage.AngebotAnfrageRepository;
import io.remedymatch.bedarf.api.BedarfDTO;
import io.remedymatch.bedarf.api.BedarfMapper;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageRepository;
import io.remedymatch.institution.domain.InstitutionRepository;
import io.remedymatch.institution.domain.InstitutionService;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.remedymatch.institution.api.InstitutionMapper.mapToDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {

    private final InstitutionRepository institutionsRepository;
    private final PersonRepository personRepository;
    private final UserProvider userProvider;
    private final BedarfAnfrageRepository bedarfAnfrageRepository;
    private final AngebotAnfrageRepository angebotAnfrageRepository;
    private final InstitutionService institutionService;

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody InstitutionDTO institution) {
        val person = personRepository.findByUsername(userProvider.getUserName());

        if (!person.getInstitution().getId().equals(institution.getId())) {
            return ResponseEntity.status(403).build();
        }

        var savedInstitution = institutionsRepository.findById(institution.getId());

        if (savedInstitution.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        institutionService.updateInstitution(savedInstitution.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/typ")
    public ResponseEntity<List<String>> typenLaden() {
        val typen = Arrays.asList(
                InstitutionTyp.Krankenhaus,
                InstitutionTyp.Arzt,
                InstitutionTyp.Lieferant,
                InstitutionTyp.Privat,
                InstitutionTyp.Andere);
        return ResponseEntity.ok(typen.stream().map(InstitutionTyp::toString).collect(Collectors.toList()));
    }

    @GetMapping("/bedarf")
    public ResponseEntity<List<BedarfDTO>> bedarfLaden() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        return ResponseEntity.ok(person.getInstitution().getBedarfe().stream().map(BedarfMapper::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/angebot")
    public ResponseEntity<List<AngebotDTO>> angebotLaden() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        return ResponseEntity.ok(person.getInstitution().getAngebote().stream().map(AngebotMapper::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/assigned")
    public ResponseEntity<InstitutionDTO> institutionLaden() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        return ResponseEntity.ok(mapToDTO(person.getInstitution()));
    }

    @GetMapping("anfragen/gestellt")
    public ResponseEntity<List<AnfrageDTO>> gestellteBedarfAnfragen() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        val bedarfAnfragen = bedarfAnfrageRepository.findAllByInstitutionVon(person.getInstitution());
        val angebotAnfragen = angebotAnfrageRepository.findAllByInstitutionVon(person.getInstitution());
        val anfrageDTOs = bedarfAnfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList());
        anfrageDTOs.addAll(angebotAnfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));

        return ResponseEntity.ok(anfrageDTOs);
    }

    @GetMapping("anfragen/erhalten")
    public ResponseEntity<List<AnfrageDTO>> erhalteneBedarfAnfragen() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        val bedarfAnfragen = bedarfAnfrageRepository.findAllByInstitutionAn(person.getInstitution());
        val angebotAnfragen = angebotAnfrageRepository.findAllByInstitutionAn(person.getInstitution());
        val anfrageDTOs = bedarfAnfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList());
        anfrageDTOs.addAll(angebotAnfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));

        return ResponseEntity.ok(anfrageDTOs);
    }

}
