package io.remedymatch.institution.api;

import static io.remedymatch.institution.api.InstitutionMapper.mapToDTO;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.angebot.api.AngebotController;
import io.remedymatch.angebot.api.AngebotDTO;
import io.remedymatch.angebot.domain.AngebotAnfrageRepository;
import io.remedymatch.angebot.domain.AngebotRepository;
import io.remedymatch.bedarf.api.BedarfDTO;
import io.remedymatch.bedarf.api.BedarfMapper;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageRepository;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionId;
import io.remedymatch.institution.domain.InstitutionRepository;
import io.remedymatch.institution.domain.InstitutionService;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.shared.GeoCalc;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {

    private final InstitutionRepository institutionsRepository;
    private final PersonRepository personRepository;
    private final UserProvider userProvider;
    private final BedarfAnfrageRepository bedarfAnfrageRepository;
    private final AngebotRepository angebotRepository;
    private final AngebotAnfrageRepository angebotAnfrageRepository;
    private final InstitutionService institutionService;

    @Autowired
    private AngebotController angebotController;
    
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

        institutionService.updateInstitution(InstitutionMapper.mapToEntity(institution));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/hauptstandort")
    public ResponseEntity<InstitutionEntity> updateHauptstandort(@RequestBody @Valid InstitutionStandortDTO standort) {
        val person = personRepository.findByUsername(userProvider.getUserName());
        return ResponseEntity.ok(institutionService.updateHauptstandort(person.getInstitution(), InstitutionStandortMapper.mapToEntity(standort)));
    }

    @PostMapping("/standort")
    public ResponseEntity<InstitutionEntity> standortHinzufuegen(@RequestBody @Valid InstitutionStandortDTO standort) {
        val person = personRepository.findByUsername(userProvider.getUserName());
        return ResponseEntity.ok(institutionService.standortHinzufuegen(person.getInstitution(), InstitutionStandortMapper.mapToEntity(standort)));
    }

    @DeleteMapping("/standort/{standortId}")
    public ResponseEntity<InstitutionEntity> standortEntfernen(@PathVariable("standortId") String standortId) {
        val person = personRepository.findByUsername(userProvider.getUserName());
        return ResponseEntity.ok(institutionService.standortEntfernen(person.getInstitution(), UUID.fromString(standortId)));
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
    	// FIXME: entfernen
        return angebotController.getInstituionAngebote();
    }

    @GetMapping("/assigned")
    public ResponseEntity<InstitutionDTO> institutionLaden() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        return ResponseEntity.ok(mapToDTO(person.getInstitution()));
    }

    @GetMapping("anfragen/gestellt")
    public ResponseEntity<List<AnfrageDTO>> gestellteBedarfAnfragen() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        InstitutionId institutionId = new InstitutionId(person.getInstitution().getId());

        val bedarfAnfragen = bedarfAnfrageRepository.findAllByInstitutionVon(person.getInstitution());
		val angebotAnfragen = angebotAnfrageRepository.getAngeboteFuerInstitutionVon(institutionId);
        val anfrageDTOs = bedarfAnfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList());
        anfrageDTOs.addAll(angebotAnfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));

        anfrageDTOs.forEach(a -> {
            var entfernung = GeoCalc.kilometerBerechnen(InstitutionStandortMapper.mapToEntity(a.getStandortVon()), InstitutionStandortMapper.mapToEntity(a.getStandortAn()));
            a.setEntfernung(entfernung);
        });

        return ResponseEntity.ok(anfrageDTOs);
    }

    @GetMapping("anfragen/erhalten")
    public ResponseEntity<List<AnfrageDTO>> erhalteneBedarfAnfragen() {
        val person = personRepository.findByUsername(userProvider.getUserName());
        InstitutionId institutionId = new InstitutionId(person.getInstitution().getId());

        val bedarfAnfragen = bedarfAnfrageRepository.findAllByInstitutionAn(person.getInstitution());
        val angebotAnfragen = angebotAnfrageRepository.getAngeboteFuerInstitutionAn(institutionId);
        val anfrageDTOs = bedarfAnfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList());
        anfrageDTOs.addAll(angebotAnfragen.stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));

        return ResponseEntity.ok(anfrageDTOs);
    }

}
