package io.remedymatch.dbinit;

import io.remedymatch.artikel.infrastructure.*;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * TODO Test-Code nicht für Produktion
 */
@Component
@Profile("dbinit")
@Slf4j
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ArtikelKategorieJpaRepository artikelKategorieRepository;

	@Autowired
	private ArtikelJpaRepository artikelRepository;

	@Autowired
	private ArtikelVarianteJpaRepository artikelVarianteRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initTestdaten();
	}

	@Transactional
	public void initTestdaten() {

		try {
			createKategorienUndArtikel();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Fehler beim Erstellen des initialen Datenbestandes", ex);
		}
	}

	/*
	 * Artikel -> Kategorien / Artikel / Varianten
	 */

	private void createKategorienUndArtikel() {
		if (artikelRepository.count() <= 0 && artikelKategorieRepository.count() <= 0) {
			createBehelfsmaskeArtikel();
			createDesinfektionArtikel();
			createHygieneArtikel();
			createProbeentnahmeArtikel();
			createSchutzkleidungArtikel();
			createSchutzmaskenArtikel();
		}
	}

	/*
	 * hier hilfe: createKategorie(kategorieName, icon) createArtikel(kategorieId,
	 * artikelName, artikelBeschreibung) addVariante(artikel, variante, norm,
	 * varianteBeschreibung, medizinischAuswaehlbar)
	 */

	private static final boolean MEDIZINISCH_AUSWAEHLBAR = true;
	private static final boolean MEDIZINISCH_NICHT_AUSWAEHLBAR = false;

	private void createBehelfsmaskeArtikel() {

		val kategorieId = createKategorie("Behelfs-Maske", null);

		var artikel = createArtikel(kategorieId, //
				"Mund-Nasen-Schutz", //
				"Hohe Flüssigkeitsresistenz • Gute Atmungsaktivität • Innen- und Außenflächen sind eindeutig gekennzeichnet");
		addVariante(artikel, //
				"Mund-Nasen-Schutz", //
				"Normen/Standards: - EN 14683 Typ IIR Leistung - ASTM F2100 Stufe 2 oder Stufe 3 oder gleichwertig - Flüssigkeitswiderstand bei einem Druck von mindestens 120 mmHg basierend auf ASTM F1862-07, ISO 22609 oder gleichwertig - Atmungsaktivität: MIL-M-36945C, EN 14683 Anhang C, oder gleichwertig - Filtrationseffizienz: ASTM F2101, EN 14683 Anhang B oder gleichwertige Normenwiederverwendbar (aus robustem Material, das gereinigt und desinfiziert werden kann) oder Einwegartikel sein", //
				"Hohe Flüssigkeitsresistenz • Gute Atmungsaktivität • Innen- und Außenflächen sind eindeutig gekennzeichnet", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Vollgesichtsschutz", //
				"Vollgesichtsschutz");
		addVariante(artikel, //
				"Vollgesichtsschutz", //
				"", //
				"Vollgesichtsschutz", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Selbst hergestellt", //
				"KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.");
		addVariante(artikel, //
				"Selbst hergestellt", //
				"", //
				"KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.", //
				MEDIZINISCH_NICHT_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Essener Modell", //
				"selbstgenähe Maske, KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.");
		addVariante(artikel, //
				"Essener Modell", //
				"", //
				"selbstgenähe Maske, KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.", //
				MEDIZINISCH_NICHT_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Papiermaske", //
				"KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.");
		addVariante(artikel, //
				"Papiermaske", //
				"", //
				"KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.", //
				MEDIZINISCH_NICHT_AUSWAEHLBAR);
	}

	private void createDesinfektionArtikel() {

		val kategorieId = createKategorie("Desinfektion", null);

		var artikel = createArtikel(kategorieId, //
				"Handdesinfektion viruzid", //
				"Handdesinfektion viruzid");
		addVariante(artikel, //
				"Handdesinfektion viruzid", //
				"", //
				"Handdesinfektion viruzid", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Handdesinfektion nach WHO Standards", //
				"Handdesinfektion nach WHO Standards");
		addVariante(artikel, //
				"Handdesinfektion nach WHO Standards", //
				"", //
				"Handdesinfektion nach WHO Standards", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Handdesinfektion begrenzt viruzid", //
				"Handdesinfektion begrenzt viruzid");
		addVariante(artikel, //
				"Handdesinfektion begrenzt viruzid", //
				"", //
				"Handdesinfektion begrenzt viruzid", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Flächendesinfektion viruzid", //
				"Flächendesinfektion viruzid");
		addVariante(artikel, //
				"Flächendesinfektion viruzid", //
				"", //
				"Flächendesinfektion viruzid", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Desinfektionslösung in Kittelflasche", //
				"Desinfektionslösung in Kittelflasche");
		addVariante(artikel, //
				"Desinfektionslösung in Kittelflasche", //
				"", //
				"Desinfektionslösung in Kittelflasche", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Ethanol", //
				"Ethanol");
		addVariante(artikel, //
				"Ethanol", //
				"", //
				"Ethanol", //
				MEDIZINISCH_AUSWAEHLBAR);
	}

	private void createHygieneArtikel() {

		val kategorieId = createKategorie("Hygiene", null);

		var artikel = createArtikel(kategorieId, //
				"Einweg-Sitzbezüge", //
				"Einweg-Sitzbezüge");
		addVariante(artikel, //
				"Einweg-Sitzbezüge", //
				"", //
				"Einweg-Sitzbezüge", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Flüssigseife", //
				"Flüssigseife");
		addVariante(artikel, //
				"Flüssigseife", //
				"", //
				"Flüssigseife", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Einweghandtücher", //
				"Einweghandtücher");
		addVariante(artikel, //
				"Einweghandtücher", //
				"", //
				"Einweghandtücher", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Toilettenpapier", //
				"Toilettenpapier");
		addVariante(artikel, //
				"Toilettenpapier", //
				"", //
				"Toilettenpapier", //
				MEDIZINISCH_AUSWAEHLBAR);
	}

	private void createProbeentnahmeArtikel() {

		val kategorieId = createKategorie("Probenentnahme", null);

		var artikel = createArtikel(kategorieId, //
				"Abstrichtupfer", //
				"Abstrichtupfer");
		addVariante(artikel, //
				"Abstrichtupfer", //
				"", //
				"Abstrichtupfer", //
				MEDIZINISCH_AUSWAEHLBAR);
	}

	private void createSchutzkleidungArtikel() {

		val kategorieId = createKategorie("Schutzkleidung", null);

		var artikel = createArtikel(kategorieId, //
				"Kittel", //
				"Kittel");
		addVariante(artikel, //
				"S", //
				"", //
				"Kittel Gr. S", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"M", //
				"", //
				"Kittel Gr. M", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"L", //
				"", //
				"Kittel Gr. L", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"XL", //
				"", //
				"Kittel Gr. XL", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"XXL", //
				"", //
				"Kittel Gr. XXL", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"Unisize", //
				"", //
				"Kittel Gr. Unisize", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Schutzbrillen", //
				"Schutzbrillen");
		addVariante(artikel, //
				"Schutzbrillen", //
				"", //
				"Schutzbrillen", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Overall", //
				"Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle");
		addVariante(artikel, //
				"S", //
				"", //
				"Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"M", //
				"", //
				"Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"L", //
				"", //
				"Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"XL", //
				"", //
				"Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"XXL", //
				"", //
				"Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"Unisize", //
				"", //
				"Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"Einmalhandschuhe", //
				"Einmalhandschuhe");
		addVariante(artikel, //
				"XS", //
				"", //
				"Einmalhandschuhe XS", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"S", //
				"", //
				"Einmalhandschuhe S", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"M", //
				"", //
				"Einmalhandschuhe M", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"L", //
				"", //
				"Einmalhandschuhe L", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"XL", //
				"", //
				"Einmalhandschuhe XL", //
				MEDIZINISCH_AUSWAEHLBAR);
		addVariante(artikel, //
				"XXL", //
				"", //
				"Einmalhandschuhe XXL", //
				MEDIZINISCH_AUSWAEHLBAR);
	}

	private void createSchutzmaskenArtikel() {

		val kategorieId = createKategorie("Schutzmasken", null);

		var artikel = createArtikel(kategorieId, //
				"FFP2",
				"Atemschutzgerät \"N95\" gemäß FDA Klasse II, unter 21 CFR 878.4040, und CDC NIOSH, oder \"FFP2\" gemäß EN 149 Verordnung 2016/425 Kategorie III oder gleichwertige Normen");
		addVariante(artikel, //
				"FFP2",
				"Atmungsaktives Design, das nicht gegen den Mund zusammenfällt (z.B. Entenschnabel, becherförmig)Ausgestattet mit Ausatemventil • Versehen mit einer Metallplatte an der Nasenspitze • Kann wiederverwendbar (aus robustem Material, das gereinigt und desinfiziert werden kann) oder Einwegartikel sein", //
				"Atemschutzgerät \"N95\" gemäß FDA Klasse II, unter 21 CFR 878.4040, und CDC NIOSH, oder \"FFP2\" gemäß EN 149 Verordnung 2016/425 Kategorie III oder gleichwertige Normen", //
				MEDIZINISCH_AUSWAEHLBAR);

		artikel = createArtikel(kategorieId, //
				"FFP3", //
				"ausgestattet mit Ausatemventil");
		addVariante(artikel, //
				"FFP3", //
				"Normen/Standards: - \"FFP3\" gemäß EN 149:2001+A1 oder gleichwertige Normen", //
				"ausgestattet mit Ausatemventil", //
				MEDIZINISCH_AUSWAEHLBAR);

	}

	private UUID createKategorie(final String name, final String icon) {
		return artikelKategorieRepository.save(ArtikelKategorieEntity.builder() //
				.name(name) //
				.icon(icon) //
				.build()).getId();
	}

	private ArtikelEntity createArtikel(//
			final UUID kategorieId, //
			final String name, //
			final String beschreibung) {
		return artikelRepository.save(ArtikelEntity.builder() //
				.artikelKategorie(kategorieId) //
				.name(name) //
				.beschreibung(beschreibung) //
				.build());
	}

	private ArtikelVarianteEntity addVariante(//
											  final ArtikelEntity artikel, //
											  final String variante, //
											  final String norm, //
											  final String beschreibung, //
											  final boolean medizinischAuswaehlbar) {
		ArtikelVarianteEntity artikelVariante = ArtikelVarianteEntity.builder() //
				.artikel(artikel.getId()) //
				.variante(variante) //
				.norm(StringUtils.stripToNull(norm)) //
				.beschreibung(beschreibung) //
				.medizinischAuswaehlbar(medizinischAuswaehlbar) //
				.build();
		return artikelVarianteRepository.save(artikelVariante);
	}
}
