package io.remedymatch.institution.api;

import io.remedymatch.bedarf.api.BedarfDTO;
import io.remedymatch.bedarf.api.BedarfMapper;
import io.remedymatch.institution.domain.InstitutionRepository;
import io.remedymatch.person.domain.PersonRepository;
import io.remedymatch.web.UserNameProvider;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
public class InstitutionController {

    private final InstitutionRepository institutionsRepository;
    private final PersonRepository personRepository;

    private final UserNameProvider userNameProvider;

    @GetMapping
    public ResponseEntity<List<InstitutionDTO>> alleLaden() {
        val institutions = StreamSupport.stream(institutionsRepository.findAll().spliterator(), false)
                .map(InstitutionMapper::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(institutions);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody InstitutionDTO institutionDTO) {
        var institution = institutionsRepository.findById(institutionDTO.getId());

        if (institution.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var updateInstitution = institution.get();
        updateInstitution.setName(institutionDTO.getName());
        institutionsRepository.save(updateInstitution);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/bedarf")
    public ResponseEntity<List<BedarfDTO>> bedarfLaden() {
        val person = personRepository.findByUserName(userNameProvider.getUserName());
        return ResponseEntity.ok(person.getInstitution().getBedarfe().stream().map(BedarfMapper::mapToDTO).collect(Collectors.toList()));
    }


}
