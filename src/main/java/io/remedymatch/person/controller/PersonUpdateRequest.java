package io.remedymatch.person.controller;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
class PersonUpdateRequest {

	private UUID aktuellesStandortId;
	
	boolean hasAenderungen() {
		return aktuellesStandortId != null;
	}
}
