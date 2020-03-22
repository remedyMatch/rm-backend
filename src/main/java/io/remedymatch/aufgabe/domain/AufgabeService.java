package io.remedymatch.aufgabe.domain;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AufgabeService {


    public List<String> aufgabenLaden() {
        return Arrays.asList("Aufgabe1", "Aufgabe2", "Aufgabe3");
    }

}
