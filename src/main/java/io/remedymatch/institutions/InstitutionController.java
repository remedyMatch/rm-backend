package io.remedymatch.institutions;

import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@AllArgsConstructor
@RequestMapping("/institutions")
public class InstitutionController {

    private final InstitutionRepository institutionsRepository;

    @GetMapping
    public ResponseEntity<List<InstitutionDTO>> getAll(){
        val institutions =  StreamSupport.stream(institutionsRepository.findAll().spliterator(), false)
                .map(this::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(institutions);
    }


    @PutMapping
    public ResponseEntity<Void> update(@RequestBody InstitutionDTO institutionDTO){
        var institution = institutionsRepository.findById(institutionDTO.getId());

        if(institution.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var updateInstitution = institution.get();
        updateInstitution.setName(institutionDTO.getName());
        institutionsRepository.save(updateInstitution);

        return ResponseEntity.ok().build();
    }

    private InstitutionDTO mapToDTO(InstitutionEntity entity){
        return InstitutionDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .build();
    }


}
