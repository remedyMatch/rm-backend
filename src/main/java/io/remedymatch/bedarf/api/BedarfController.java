package io.remedymatch.bedarf.api;

import static io.remedymatch.bedarf.api.BedarfMapper.mapToDTO;
import static io.remedymatch.bedarf.api.BedarfMapper.mapToEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.bedarf.domain.BedarfRepository;
import io.remedymatch.bedarf.domain.BedarfService;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageRepository;
import io.remedymatch.bedarf.domain.anfrage.BedarfAnfrageService;
import io.remedymatch.institution.api.AnfrageDTO;
import io.remedymatch.institution.api.InstitutionStandortMapper;
import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.shared.GeoCalc;
import io.remedymatch.user.domain.UserService;
import io.remedymatch.web.UserProvider;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf")
public class BedarfController {

    private final BedarfService bedarfService;
    private final UserService userService;
    private final BedarfAnfrageService bedarfAnfrageService;
    private final BedarfAnfrageRepository bedarfAnfrageRepository;
    private final BedarfRepository bedarfRepository;

    @GetMapping()
    public ResponseEntity<List<BedarfDTO>> bedarfeLaden() {
        val user = userService.getContextUser();

        val bedarfe = bedarfRepository.findAllByBedientFalse().stream()
                .filter(bedarf -> !bedarf.isBedient()).map(BedarfMapper::mapToDTO).collect(Collectors.toList());

        bedarfe.forEach(b -> {
            var entfernung = GeoCalc.kilometerBerechnen(user.getInstitution().getHauptstandort(), InstitutionStandortMapper.mapToStandort(b.getStandort()));
            b.setEntfernung(entfernung);
        });

        return ResponseEntity.ok(bedarfe);
    }

    @GetMapping("/institution")
	public ResponseEntity<List<BedarfDTO>> getInstituionBedarfee() {
    	
    	val user = userService.getContextUser();

		return ResponseEntity.ok(bedarfRepository.findAllByInstitution_Id(user.getInstitution().getId().getValue()).stream()//
				.map(BedarfMapper::mapToDTO)//
				.collect(Collectors.toList()));
	}
    
    @PostMapping
    public ResponseEntity<Void> bedarfMelden(@RequestBody @Valid BedarfDTO bedarf) {
    	val user = userService.getContextUser();
        bedarfService.bedarfMelden(mapToEntity(bedarf), InstitutionEntityConverter.convert(user.getInstitution()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> bedarfLoeschen(@PathVariable("id") String bedarfId) {
    	val user = userService.getContextUser();
        val bedarf = bedarfService.bedarfLaden(bedarfId);

        if (bedarf.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!bedarf.get().getInstitution().getId().equals(user.getInstitution().getId())) {
            return ResponseEntity.status(403).build();
        }

        bedarfService.bedarfLoeschen(UUID.fromString(bedarfId));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bedienen")
    public ResponseEntity<Void> bedarfBedienen(@RequestBody BedarfBedienenRequest request) {
    	val user = userService.getContextUser();
        bedarfService.starteAnfrage(
                request.getBedarfId(),
                InstitutionEntityConverter.convert(user.getInstitution()),
                request.getKommentar(),
                request.getStandortId(),
                request.getAnzahl());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/anfragen")
    public ResponseEntity<List<AnfrageDTO>> anfragenLaden(@PathVariable("id") String bedarfId) {
//        val bedarf = bedarfService.bedarfLaden(bedarfId);
//
//        if (bedarf.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(bedarf.get().getAnfragen().stream().map(AnfrageMapper::mapToDTO).collect(Collectors.toList()));
    	
    	// FIXME
    	return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BedarfDTO> bedarfLaden(@PathVariable("id") String bedarfId) {
        val bedarf = bedarfService.bedarfLaden(bedarfId);

        if (bedarf.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDTO(bedarf.get()));
    }

    @DeleteMapping("/anfrage/{id}")
    public ResponseEntity<Void> anfrageStornieren(@PathVariable("id") String anfrageId) {

    	val user = userService.getContextUser();
        val anfrage = bedarfAnfrageRepository.findById(UUID.fromString(anfrageId));

        if (anfrage.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!anfrage.get().getInstitutionVon().getId().equals(user.getInstitution().getId())
                && !anfrage.get().getInstitutionAn().getId().equals(user.getInstitution().getId())) {
            return ResponseEntity.status(403).build();
        }

        bedarfAnfrageService.anfrageStornieren(anfrageId);
        return ResponseEntity.ok().build();
    }
}
