package io.remedymatch.match.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.remedymatch.institution.domain.InstitutionEntityConverter;
import io.remedymatch.match.domain.MatchService;
import io.remedymatch.shared.GeoCalc;
import io.remedymatch.user.domain.UserService;
import lombok.AllArgsConstructor;
import lombok.val;

@RestController
@AllArgsConstructor
@RequestMapping("/match")
public class MatchController {

	private final UserService userService;
	private final MatchService matchService;

	@GetMapping
	public ResponseEntity<List<MatchDTO>> beteiligteMatches() {
		val user = userService.getContextUser();

		val matches = matchService.beteiligteMatches(InstitutionEntityConverter.convert(user.getInstitution())).stream()
				.map(MatchMapper::mapToDTO).collect(Collectors.toList());

		matches.forEach(m -> {
			var entfernung = GeoCalc.kilometerBerechnen(MatchStandortMapper.mapToEntity(m.getStandortVon()),
					MatchStandortMapper.mapToEntity(m.getStandortAn()));
			m.setEntfernung(entfernung);
		});

		return ResponseEntity.ok(matches);
	}
}
