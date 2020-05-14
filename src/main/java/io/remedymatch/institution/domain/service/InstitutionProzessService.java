package io.remedymatch.institution.domain.service;

import io.remedymatch.engine.client.EngineClient;
import io.remedymatch.engine.domain.BusinessKey;
import io.remedymatch.engine.domain.ProzessKey;
import io.remedymatch.institution.domain.model.InstitutionAntrag;
import io.remedymatch.person.domain.model.PersonId;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;

@AllArgsConstructor
@Validated
@Service
@Transactional
public class InstitutionProzessService {

    public static final ProzessKey ANTRAG_PROZESS_KEY = new ProzessKey("institution_antrag_prozess");
    public static final String VAR_INSTITUTION_ROLLE = "institution_rolle";
    public static final String VAR_INSTITUTION_NAME = "institution_name";
    public static final String VAR_INSTITUTION_TYP = "institution_typ";
    public static final String VAR_INSTITUTION_WEBSEITE = "institution_webseite";
    public static final String VAR_INSTITUTION_STRASSE = "institution_strasse";
    public static final String VAR_INSTITUTION_HAUSNUMMER = "institution_hausnummer";
    public static final String VAR_INSTITUTION_PLZ = "institution_plz";
    public static final String VAR_INSTITUTION_ORT = "institution_ort";
    public static final String VAR_INSTITUTION_LAND = "institution_land";
    public static final String VAR_ANTRAGSTELLER = "antragsteller";
    public static final String VAR_INSTITUTION_ID = "institution_id";


    private final EngineClient engineClient;

    public void antragProzessStarten(InstitutionAntrag antrag) {
        val variables = new HashMap<String, Object>();
        variables.put(VAR_INSTITUTION_ROLLE, antrag.getRolle().toString());
        variables.put(VAR_INSTITUTION_NAME, antrag.getName());
        variables.put(VAR_INSTITUTION_TYP, antrag.getInstitutionTyp().toString());
        variables.put(VAR_INSTITUTION_WEBSEITE, antrag.getWebseite());
        variables.put(VAR_INSTITUTION_STRASSE, antrag.getStrasse());
        variables.put(VAR_INSTITUTION_HAUSNUMMER, antrag.getHausnummer());
        variables.put(VAR_INSTITUTION_PLZ, antrag.getPlz());
        variables.put(VAR_INSTITUTION_ORT, antrag.getOrt());
        variables.put(VAR_INSTITUTION_LAND, antrag.getLand());
        variables.put(VAR_ANTRAGSTELLER, antrag.getAntragsteller());

        engineClient.prozessStarten(
                ANTRAG_PROZESS_KEY,
                new BusinessKey(antrag.getId().getValue()),
                new PersonId(antrag.getAntragsteller()),
                variables);

    }
}
