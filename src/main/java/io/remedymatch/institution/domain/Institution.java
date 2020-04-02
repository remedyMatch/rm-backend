package io.remedymatch.institution.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Institution {
    private InstitutionId id;
    private String name;
    private String institutionKey;
    private InstitutionTyp typ;
    private InstitutionStandort  hauptstandort;	
    @Builder.Default
    private List<InstitutionStandort> standorte = new ArrayList<>();
}
