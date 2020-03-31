package io.remedymatch.institution.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import io.remedymatch.angebot.domain.AngebotAnfrageStatus;
import io.remedymatch.angebot.infrastructure.AngebotAnfrageEntity;
import io.remedymatch.angebot.infrastructure.AngebotEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class InstitutionStandortEntity {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@Column(length = 60)
	private String name;

	@Column
	private String plz;

	@Column
	private String ort;

	@Column
	private String strasse;

	@Column
	private String land;

	@Column
	private double longitude;

	@Column
	private double latitude;

	public String getAdresse() {
		return strasse + ", " + plz + " " + ort + ", " + land;
	}

}
