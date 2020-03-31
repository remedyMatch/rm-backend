package io.remedymatch.standort.api;

import io.remedymatch.standort.geocoding.domain.Point;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/standort")
public class StandortController {

    private final StandortService standortService;

    @GetMapping("/koordinaten")
    public ResponseEntity<List<Point>> koordinatenByUnstrukturierterAdressString(@RequestParam(name = "adresse") String adresse) {
        if (StringUtils.isEmpty(adresse)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(standortService.findePointsByAdressString(adresse));
    }
}
