package io.remedymatch.geodaten.geocoding.api;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.geodaten.domain.StandortService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/standort")
public class StandortController {

    private final StandortService standortService;

    @GetMapping("/vorschlaege")
    public ResponseEntity<List<String>> findeAdressVorschlaegeByAdressString(@RequestParam(name = "standort")
                                                                                         String adressString) {
        if (StringUtils.isEmpty(adressString)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(standortService.findeAdressVorschlaegeByAdressString(adressString));
    }

}
