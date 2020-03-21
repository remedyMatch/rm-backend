package io.remedymatch.persons;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PersonDTO {
    private UUID id;
    private String userName;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
}
