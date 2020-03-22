package io.remedymatch.bedarf.domain;

import io.remedymatch.engine.EngineClient;
import io.remedymatch.institution.domain.InstitutionEntity;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@AllArgsConstructor
@Service
public class BedarfService {

    private final BedarfRepository bedarfRepository;
    private final EngineClient engineClient;
    private final BedarfAnfrageRepository bedarfAnfrageRepository;

    @Transactional
    public void bedarfMelden(BedarfEntity bedarf, InstitutionEntity institutionEntity) {
        //TODO Prozess starten?
        bedarf.setInstitution(institutionEntity);
        bedarfRepository.save(bedarf);
    }

    @Transactional
    public Iterable<BedarfEntity> alleBedarfeLaden() {
        return bedarfRepository.findAll();
    }

    @Transactional
    public void bedarfLoeschen(UUID id) {
        val bedarf = bedarfRepository.findById(id);
        bedarfRepository.delete(bedarf.get());
    }

    @Transactional
    public void bedarfUpdaten(BedarfEntity bedarf) {
        val oldBedarf = bedarfRepository.findById(bedarf.getId()).get();
        oldBedarf.setAnzahl(bedarf.getAnzahl());
        oldBedarf.setArtikel(bedarf.getArtikel());
        bedarfRepository.save(oldBedarf);
    }


    @Transactional
    public void starteAnfrage(UUID bedarfId, InstitutionEntity anfrager, String kommentar) {

        val bedarf = bedarfRepository.findById(bedarfId);

        if (bedarf.isEmpty()) {
            throw new IllegalArgumentException("Bedarf ist nicht vorhanden");
        }

        BedarfAnfrageEntity.builder()
                .anfrager(anfrager)
                .kommentar(kommentar)
                .bedarf(bedarf.get())
                .build();

//        val variables = Variables.createVariables();
//        variables.putValue("objektId", bedarf.get().getId());
//        variables.putValue("institution", anfrager.getId());
//
//        val processInstance = engineClient.runtimeService.startProcessInstanceByKey(PROCESS_KEY, bedarf.get().getId().toString(), variables);


    }

    @Transactional
    public void anfrageBeantworten(String taskId) {

//        val task = engineClient.taskService.createTaskQuery().taskId(taskId).singleResult();
//
//        if (task == null) {
//            throw new IllegalArgumentException("Task ist nicht vorhanden");
//        }

//        val anfrage = bedarfAnfrageRepository.findById(anfrageId);
//
//        if (anfrage.isEmpty()) {
//            throw new IllegalArgumentException("Anfrage ist nicht vorhanden");
//        }


    }
}
