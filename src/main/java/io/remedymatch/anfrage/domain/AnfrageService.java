package io.remedymatch.anfrage.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AnfrageService {

    private final AnfrageRepository anfrageRepository;


}
