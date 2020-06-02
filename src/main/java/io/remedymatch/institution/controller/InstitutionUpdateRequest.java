package io.remedymatch.institution.controller;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
class InstitutionUpdateRequest {

    @NotBlank
    private String name;

}
