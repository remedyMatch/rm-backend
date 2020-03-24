package io.remedymatch.engine;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class TaskDTO {

    public String taskId;
    public String prozessInstanceId;
    public String institution;
    public String objektId;
    public String taskKey;

}
