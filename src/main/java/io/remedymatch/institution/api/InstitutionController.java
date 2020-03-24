package io.remedymatch.institution.api;

import io.remedymatch.anfrage.api.AnfrageDTO;
import io.remedymatch.anfrage.api.AnfrageMapper;
import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.angebot.api.AngebotDTO;
import io.remedymatch.angebot.api.AngebotMapper;
import io.remedymatch.bedarf.api.BedarfDTO;
import io.remedymatch.bedarf.api.BedarfMapper;
import io.remedymatch.institution.domain.InstitutionRepository;
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
import java.util.stream.StreamSupport;

import static io.remedymatch.institution.api.InstitutionMapper.mapToDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {

    private final InstitutionRepository institutionsRepository;
    private final PersonRepository personRepository;
    private final UserProvider userProvider;
    private final AnfrageRepository anfrageRepository;

    @GetMapping
    public ResponseEntity<List<InstitutionDTO>> alleLaden() {
        val institutions = StreamSupport.stream(institutionsRepository.findAll().spliterator(), false)
                .map(InstitutionMapper::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(institutions);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody InstitutionDTO institutionDTO) {
        val person = personRepository.findByUsername(userProvider.getUserName());

        if (!person.getInstitution().getId().equals(institutionDTO.getId())) {
            return ResponseEntity.status(403).build();
        }

        var institution = institutionsRepository.findById(institutionDTO.getId());

        if (institution.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var updateInstitution = institution.get();
        updateInstitution.setName(institutionDTO.getName());
        updateInstitution.setStandort(institutionDTO.getStandort());
        updateInstitution.setTyp(institutionDTO.getTyp());
        institutionsRepository.save(updateInstitution);

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
    public ResponseEntity<List<AnfrageDTO>> gestellteAnfragen() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        val anfragen = anfrageRepository.findAllByInstitutionVon(person.getInstitution());
        return ResponseEntity.ok(anfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));
    }

    @GetMapping("anfragen/erhalten")
    public ResponseEntity<List<AnfrageDTO>> erhalteneAnfragen() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        val anfragen = anfrageRepository.findAllByInstitutionAn(person.getInstitution());
        return ResponseEntity.ok(anfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));
    }


}
