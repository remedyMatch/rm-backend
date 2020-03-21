package io.remedymatch.angebot.api;

import io.remedymatch.angebot.domain.AngebotRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@AllArgsConstructor
@RequestMapping("/institution")
public class AngebotController {

    private final AngebotRepository institutionsRepository;

    @GetMapping
    public ResponseEntity<List<AngebotDTO>> getAll() {
        val institutions = StreamSupport.stream(institutionsRepository.findAll().spliterator(), false)
                .map(AngebotMapper::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(institutions);
    }

}
