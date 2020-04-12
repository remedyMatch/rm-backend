package io.remedymatch.person.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
class PersonRO {
    private UUID id;
    private String username;
    private String vorname;
    private String nachname;
    private String email;
    private String telefon;
}
