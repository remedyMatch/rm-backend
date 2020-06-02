package io.remedymatch.institution.controller;

import io.remedymatch.institution.domain.model.InstitutionAntrag;

class InstitutionAntragMapper {

    public static InstitutionAntragRO mapToRO(InstitutionAntrag antrag) {
        return InstitutionAntragRO.builder() //
                .id(antrag.getId().getValue()) //
                .name(antrag.getName()) //
                .hausnummer(antrag.getHausnummer()) //
                .land(antrag.getLand()) //
                .ort(antrag.getOrt()) //
                .plz(antrag.getPlz()) //
                .rolle(antrag.getRolle()) //
                .strasse(antrag.getStrasse()) //
                .status(antrag.getStatus()) //
                .institutionTyp(antrag.getInstitutionTyp()) //
                .build();
    }

}
