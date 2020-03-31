package io.remedymatch.angebot.domain.aufgabe;

import io.remedymatch.anfrage.domain.AnfrageRepository;
import io.remedymatch.angebot.domain.AngebotService;
import io.remedymatch.aufgabe.domain.handler.TaskCompleteHandler;
import io.remedymatch.bedarf.domain.BedarfService;
import io.remedymatch.engine.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Component
public class AnfrageBearbeitenTaskCompleteHandler implements TaskCompleteHandler {

    private final AnfrageRepository anfrageRepository;
    private final AngebotService angebotService;
    private final BedarfService bedarfService;

    @Override
    public void taskPruefen(TaskDTO taskDTO, Map<String, Object> variables) {

        if(!Boolean.parseBoolean(variables.get(AnfrageBearbeitenTaskContstants.ANGENOMMEN).toString())){
            return;
        }

        val anfrage = anfrageRepository.findById(UUID.fromString(taskDTO.getObjektId()));
        if(anfrage.isEmpty()){
            throw new IllegalArgumentException("Anfrage mit der Id: "+taskDTO.getObjektId()+" nicht vorhanden");
        }

        //Anfrage annehmen
        if(anfrage.get().getBedarf() != null){
            bedarfService.anfrageAnnehmen(taskDTO.getObjektId());
        }else if(anfrage.get().getAngebot() != null){
            angebotService.anfrageAnnehmen(taskDTO.getObjektId());
        }
    }

    @Override
    public String taskKey() {
        return AnfrageBearbeitenTaskContstants.TASK_KEY;
    }
}
