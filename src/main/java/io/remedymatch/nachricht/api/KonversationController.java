package io.remedymatch.nachricht.api;


import io.remedymatch.nachricht.domain.model.KonversationId;
import io.remedymatch.nachricht.domain.model.NeueNachricht;
import io.remedymatch.nachricht.domain.service.NachrichtService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/konversation")
@Validated
@Transactional
public class KonversationController {

    private final NachrichtService nachrichtService;

    @GetMapping("/{id}")
    public ResponseEntity<KonversationRO> ladeKonversation(@PathVariable("id") UUID konversationId) {
        val konversation = nachrichtService.konversationLaden(new KonversationId(konversationId));
        return ResponseEntity.ok(KonversationMapper.map(konversation));
    }

    @GetMapping
    public ResponseEntity<List<KonversationRO>> ladeAlleBeteiligteKonversationen() {
        val konversationen = nachrichtService.beteiligteKonversationenLaden();
        return ResponseEntity.ok(KonversationMapper.map(konversationen));
    }

    @PostMapping("/{id}/nachricht")
    public ResponseEntity<Void> nachrichtAnKonversationSenden(
            @PathVariable("id") UUID konversationId,
            @RequestBody @Valid NeueNachricht nachricht) {
        nachrichtService.nachrichtSenden(new KonversationId(konversationId), nachricht);
        return ResponseEntity.ok().build();
    }
}
