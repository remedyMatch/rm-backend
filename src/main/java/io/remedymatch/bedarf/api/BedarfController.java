package io.remedymatch.bedarf.api;

import io.remedymatch.bedarf.domain.BedarfAnfrageId;
import io.remedymatch.bedarf.domain.BedarfRepository;
import io.remedymatch.bedarf.domain.BedarfService;
import io.remedymatch.domain.ObjectNotFoundException;
import io.remedymatch.institution.api.AnfrageDTO;
import io.remedymatch.institution.domain.InstitutionStandortId;
import io.remedymatch.user.domain.NotUserInstitutionObjectException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.remedymatch.bedarf.api.BedarfMapper.*;

@RestController
@AllArgsConstructor
@RequestMapping("/bedarf")
@Validated
public class BedarfController {

    private final BedarfService bedarfService;
    private final BedarfRepository bedarfRepository;
//    private final UserService userService;
//    private final BedarfAnfrageService bedarfAnfrageService;
//    private final BedarfAnfrageRepository bedarfAnfrageRepository;
//    private final BedarfRepository bedarfRepository;

    @GetMapping
    public ResponseEntity<List<BedarfDTO>> getAll() {
        return ResponseEntity.ok(bedarfService.getAlleNichtBedienteBedarfe().stream()//
                .map(BedarfMapper::mapToDTO)//
                .collect(Collectors.toList()));
    }

    @GetMapping("/institution")
    public ResponseEntity<List<BedarfDTO>> getInstituionBedarfe() {
        return ResponseEntity.ok(bedarfService.getBedarfeDerUserInstitution().stream()//
                .map(BedarfMapper::mapToDTO)//
                .collect(Collectors.toList()));
    }

    @PostMapping("/v2")
    public ResponseEntity<BedarfDTO> neuesBedarfEinstellen(@RequestBody @Valid NeuesBedarfRequest neuesBedarf) {
        return ResponseEntity.ok(mapToDTO(bedarfService.neuesBedarfEinstellen(mapToNeuesBedarf(neuesBedarf))));
    }

    @PostMapping
    public ResponseEntity<Void> bedarfMelden(@RequestBody @Valid BedarfDTO bedarf) {
        bedarfService.bedarfMelden(mapToBedarf(bedarf));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BedarfDTO> bedarfLaden(@PathVariable("id") String bedarfId) {
        val bedarf = bedarfRepository.get(maptToBedarfId(bedarfId));

        if (bedarf.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapToDTO(bedarf.get()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> bedarfLoeschen(@PathVariable("id") String bedarfId) {
        try {
            bedarfService.bedarfDerUserInstitutionLoeschen(maptToBedarfId(bedarfId));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }

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

    @PostMapping("/bedienen")
    public ResponseEntity<Void> bedarfBedienen(@RequestBody @Valid BedarfBedienenRequest request) {
        bedarfService.bedarfAnfrageErstellen(//
                maptToBedarfId(request.getBedarfId()), //
                new InstitutionStandortId(request.getStandortId()), //
                request.getKommentar(), //
                request.getAnzahl());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/anfrage/{id}")
    public ResponseEntity<Void> anfrageStornieren(@PathVariable("id") String anfrageId) {
        try {
            bedarfService.bedarfAnfrageDerUserInstitutionLoeschen(new BedarfAnfrageId(UUID.fromString(anfrageId)));
        } catch (ObjectNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotUserInstitutionObjectException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok().build();
    }
}
