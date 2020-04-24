package io.remedymatch.engine;

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
public class TaskDTO {

	private String taskId;
	private String prozessInstanceId;
	private String institution;
	private String objektId;
	private String displayName;
	private String taskKey;
	private String taskName;
}
