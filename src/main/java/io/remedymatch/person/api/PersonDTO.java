package io.remedymatch.person.api;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
class PersonDTO {
    private UUID id;
    private String userName;
    private String vorname;
    private String nachname;
    private String telefon;
}
