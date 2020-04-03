package io.remedymatch.match.infrastructure;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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
@Entity(name = "MatchStandort")
@Table(name = "RM_MATCH_STANDORT")
public class MatchStandortEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Type(type = "uuid-char")
	@Column(name = "UUID", unique = true, nullable = false, updatable = false, length = 36)
	private UUID id;

	@Type(type = "uuid-char")
	@Column(name = "INSTITUTION_STANDORT_ID", nullable = false, updatable = false, length = 36)
	private UUID institutionStandortId;

	@Column(name = "NAME", nullable = false, updatable = true, length = 64)
	private String name;

	@Column(name = "PLZ", nullable = false, updatable = true, length = 8)
	private String plz;

	@Column(name = "ORT", nullable = false, updatable = true, length = 64)
	private String ort;

	@Column(name = "STRASSE", nullable = false, updatable = true, length = 64)
	private String strasse;

	@Column(name = "LAND", nullable = false, updatable = true, length = 64)
	private String land;

	@Column(name = "LONGITUDE", nullable = true, updatable = true)
	private BigDecimal longitude;

	@Column(name = "LATITUDE", nullable = true, updatable = true)
	private BigDecimal latitude;
}
