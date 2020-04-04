package io.remedymatch.dbinit;

import com.github.javafaker.Faker;
import io.remedymatch.angebot.infrastructure.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelEntity;
import io.remedymatch.artikel.infrastructure.ArtikelJpaRepository;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieEntity;
import io.remedymatch.artikel.infrastructure.ArtikelKategorieJpaRepository;
import io.remedymatch.bedarf.domain.BedarfRepository;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.institution.infrastructure.InstitutionEntity;
import io.remedymatch.institution.infrastructure.InstitutionJpaRepository;
import io.remedymatch.institution.infrastructure.InstitutionStandortEntity;
import io.remedymatch.person.infrastructure.PersonEntity;
import io.remedymatch.person.infrastructure.PersonJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * TODO Test-Code nicht für Produktion
 */
@Component
@Profile("dbinit")
@Slf4j
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private PersonJpaRepository personRepository;

    @Autowired
    private BedarfRepository bedarfRepository;

    @Autowired
    private AngebotJpaRepository angebotRepository;

    @Autowired
    private ArtikelJpaRepository artikelRepository;

    @Autowired
    private ArtikelKategorieJpaRepository artikelKategorieRepository;

    @Autowired
    private InstitutionJpaRepository institutionRepository;

    private Faker faker = new Faker(new Locale("de"));

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initTestdaten();
    }

    @Transactional
    public void initTestdaten() {

        try {
            createKategorien();
            createInstitutions();
            createPersonen();
            // createBedarfsListe();
            createArtikel();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Fehler beim Erstellen des initialen Datenbestandes", ex);
        }
    }

    private void createArtikel() {


        createArtikel("FFP2",
                "Atmungsaktives Design, das nicht gegen den Mund zusammenfällt (z.B. Entenschnabel, becherförmig)Ausgestattet mit Ausatemventil • Versehen mit einer Metallplatte an der Nasenspitze • Kann wiederverwendbar (aus robustem Material, das gereinigt und desinfiziert werden kann) oder Einwegartikel sein",
                "Atemschutzgerät \"N95\" gemäß FDA Klasse II, unter 21 CFR 878.4040, und CDC NIOSH, oder \"FFP2\" gemäß EN 149 Verordnung 2016/425 Kategorie III oder gleichwertige Normen",
                artikelKategorieRepository.findByName("Schutzmasken").get()
        );

        createArtikel("FFP3",
                "ausgestattet mit Ausatemventil",
                "Normen/Standards: - \"FFP3\" gemäß EN 149:2001+A1 oder gleichwertige Normen",
                artikelKategorieRepository.findByName("Schutzmasken").get()
        );
        // var institution = institutionRepository.findByInstitutionKey("private100");
        // createAngebot("Schutzmasken", "Köln", false, false, false, 20, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);

        createArtikel("Mund-Nasen-Schutz",
                "Hohe Flüssigkeitsresistenz • Gute Atmungsaktivität • Innen- und Außenflächen sind eindeutig gekennzeichnet",
                "Normen/Standards: - EN 14683 Typ IIR Leistung - ASTM F2100 Stufe 2 oder Stufe 3 oder gleichwertig - Flüssigkeitswiderstand bei einem Druck von mindestens 120 mmHg basierend auf ASTM F1862-07, ISO 22609 oder gleichwertig - Atmungsaktivität: MIL-M-36945C, EN 14683 Anhang C, oder gleichwertig - Filtrationseffizienz: ASTM F2101, EN 14683 Anhang B oder gleichwertige Normenwiederverwendbar (aus robustem Material, das gereinigt und desinfiziert werden kann) oder Einwegartikel sein",
                artikelKategorieRepository.findByName("Behelfs-Maske").get()
        );
        // institution = institutionRepository.findByInstitutionKey("private101");
        //createAngebot("Schutzmasken", "Augsburg", false, false, false, 30, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);

        createArtikel("Vollgesichtsschutz",
                "",
                "",
                artikelKategorieRepository.findByName("Behelfs-Maske").get()
        );
        // institution = institutionRepository.findByInstitutionKey("private101");
        // createAngebot("Schutzmasken", "Dresden", false, false, false, 50, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);

        createArtikel("Selbst hergestellt",
                "KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.",
                "",
                artikelKategorieRepository.findByName("Behelfs-Maske").get()
        );

        createArtikel("Essener Modell",
                "selbstgenähe Maske, KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.",
                "",
                artikelKategorieRepository.findByName("Behelfs-Maske").get()
        );

        createArtikel("Papiermaske",
                "KEIN medizinischer Artikel. Bietet keinen Schutz vor Infektion.",
                "",
                artikelKategorieRepository.findByName("Behelfs-Maske").get()
        );

        createArtikel("Kittel Gr. S",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Kittel Gr. M",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Kittel Gr. L",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Kittel Gr. XL",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Kittel Gr. XXL",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Kittel Gr. Unisize",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Schutzbrillen",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Overall S",
                "Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Overall M",
                "Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Overall L",
                "Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Overall XL",
                "Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Overall XXL",
                "Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Overall Unisize",
                "Einmaliger Gebrauch, Einweg, Flüssigkeitsbeständig, Mit Kapuze, Langarm, Daumen-/Fingerschlaufen oder elastischen Manschetten zur Verankerung der Ärmel an Ort und Stelle",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Einmalhandschuhe XS",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Einmalhandschuhe S",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Einmalhandschuhe M",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Einmalhandschuhe L",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Einmalhandschuhe XL",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Einmalhandschuhe XXL",
                "",
                "",
                artikelKategorieRepository.findByName("Schutzkleidung").get()
        );

        createArtikel("Abstrichtupfer",
                "",
                "",
                artikelKategorieRepository.findByName("Probenentnahme").get()
        );

        createArtikel("Handdesinfektion viruzid",
                "",
                "",
                artikelKategorieRepository.findByName("Desinfektion").get()
        );

        createArtikel("Handdesinfektion nach WHO Standards",
                "",
                "",
                artikelKategorieRepository.findByName("Desinfektion").get()
        );

        createArtikel("Handdesinfektion begrenzt viruzid",
                "",
                "",
                artikelKategorieRepository.findByName("Desinfektion").get()
        );

        createArtikel("Flächendesinfektion viruzid",
                "",
                "",
                artikelKategorieRepository.findByName("Desinfektion").get()
        );

        createArtikel("Desinfektionslösung in Kittelflasche",
                "",
                "",
                artikelKategorieRepository.findByName("Desinfektion").get()
        );

        createArtikel("Ethanol",
                "",
                "",
                artikelKategorieRepository.findByName("Desinfektion").get()
        );

        createArtikel("Einweg-Sitzbezüge",
                "",
                "",
                artikelKategorieRepository.findByName("Hygiene").get()
        );

        createArtikel("Flüssigseife",
                "",
                "",
                artikelKategorieRepository.findByName("Hygiene").get()
        );

        createArtikel("Einweghandtücher",
                "",
                "",
                artikelKategorieRepository.findByName("Hygiene").get()
        );

        createArtikel("Toilettenpapier",
                "",
                "",
                artikelKategorieRepository.findByName("Hygiene").get()
        );



    }

    private AngebotEntity createAngebot(String kommentar, InstitutionStandortEntity standort, boolean medizinisch, boolean steril, boolean originalverpackt, double anzahl, LocalDateTime haltbarkeit, ArtikelEntity artikel, InstitutionEntity institution) {
        var angebot = AngebotEntity.builder() //
                .anzahl(BigDecimal.valueOf(anzahl)) //
                .artikel(artikel) //
                .haltbarkeit(haltbarkeit) //
                .kommentar(kommentar) //
                .medizinisch(medizinisch) //
                .institution(institution) //
                .originalverpackt(originalverpackt) //
                .steril(steril) //
                .standort(standort) //
                .build();
        angebotRepository.save(angebot);
        return angebot;
    }
//
//    private void createBedarf(double anzahl, ArtikelEntity artikel, InstitutionEntity institution) {
//        var bedarf = BedarfEntity.builder() //
//                .anzahl(anzahl) //
//                .artikel(artikel)
//                .institution(institution) //
//                .build();
//        bedarfRepository.save(bedarf);
//    }
//
//    private void createBedarfsListe() {
//        var artikel = createArtikel("Chemielabor Kittel", "Gebrauchter Kittel aus dem Chemielabor des KIT, 100% Baumwolle", artikelKategorieRepository.findByName("Kittel One-Size").get());
//        createBedarf(15, artikel, institutionRepository.findByInstitutionKey("institution2"));
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP2 Zustand neu", artikelKategorieRepository.findByName("Masken FFP2").get());
//        createBedarf(6000, artikel, institutionRepository.findByInstitutionKey("institution12"));
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP2 Zustand neu", artikelKategorieRepository.findByName("Masken FFP2").get());
//        createBedarf(5000, artikel, institutionRepository.findByInstitutionKey("institution13"));
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP2", artikelKategorieRepository.findByName("Masken FFP2").get());
//        createBedarf(2500, artikel, institutionRepository.findByInstitutionKey("institution14"));
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP3 Zustand originalverpackt", artikelKategorieRepository.findByName("Masken FFP3").get());
//        createBedarf(12000, artikel, institutionRepository.findByInstitutionKey("institution11"));
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP3 neu", artikelKategorieRepository.findByName("Masken FFP3").get());
//        createBedarf(6000, artikel, institutionRepository.findByInstitutionKey("institution18"));
//
//        artikel = createArtikel("Schutzmasken", "FFP3", artikelKategorieRepository.findByName("Masken FFP3").get());
//        createBedarf(6000, artikel, institutionRepository.findByInstitutionKey("institution9"));
//
//        artikel = createArtikel("Handdesinfektion", "Kanister für Desifektion Corona", artikelKategorieRepository.findByName("Handdesinfektion").get());
//        createBedarf(20, artikel, institutionRepository.findByInstitutionKey("institution12"));
//
//        artikel = createArtikel("Handschuhe", "Größe S im Gebinde a 100 Stück", artikelKategorieRepository.findByName("Handschuhe S").get());
//        createBedarf(150, artikel, institutionRepository.findByInstitutionKey("institution13"));
//
//        artikel = createArtikel("Handschuhe", "Größe L im Gebinde a 500 Stück", artikelKategorieRepository.findByName("Handschuhe L").get());
//        createBedarf(80, artikel, institutionRepository.findByInstitutionKey("institution16"));
//
//        artikel = createArtikel("Handschuhe", "Größe XL im Gebinde a 200 Stück", artikelKategorieRepository.findByName("Handschuhe XL").get());
//        createBedarf(350, artikel, institutionRepository.findByInstitutionKey("institution16"));
//
//        artikel = createArtikel("Handschuhe", "Größe XL im Gebinde a 200 Stück", artikelKategorieRepository.findByName("Handschuhe XL").get());
//        createBedarf(250, artikel, institutionRepository.findByInstitutionKey("institution17"));
//
//        artikel = createArtikel("Schutzhandschuhe", "Einzeln", artikelKategorieRepository.findByName("Handschuhe L").get());
//        createBedarf(3000, artikel, institutionRepository.findByInstitutionKey("institution14"));
//
//        artikel = createArtikel("Brillen", "neu", artikelKategorieRepository.findByName("Brillen").get());
//        createBedarf(1500, artikel, institutionRepository.findByInstitutionKey("institution15"));
//
//        artikel = createArtikel("Infusionsbesteck", "neu", artikelKategorieRepository.findByName("Infusionsbesteck").get());
//        createBedarf(200, artikel, institutionRepository.findByInstitutionKey("institution16"));
//
//        artikel = createArtikel("Virostatika", "neu", artikelKategorieRepository.findByName("Virostatika").get());
//        createBedarf(250, artikel, institutionRepository.findByInstitutionKey("institution17"));
//
//
//    }

    private ArtikelEntity createArtikel(String name, String beschreibung, String norm, ArtikelKategorieEntity kategorie) {
        var entity = ArtikelEntity.builder() //
                .beschreibung(beschreibung) //
                .hersteller(faker.company().name()) //
                .name(name) //
                .ean((faker.number().randomNumber(13, true) + "")) //
                .norm(norm)
                .artikelKategorie(kategorie) //
                .build();
        artikelRepository.save(entity);
        return entity;
    }

    private void createPersonen() {

        for (InstitutionEntity institution : institutionRepository.findAll()) {
            /* QuickFix: Beim Initialisieren ist der Institutions-Typ der
            Institution null..hier fliegt dann waehrend der initialisierung eine NPE.
            */
            if (institution.getTyp() == null) {
                continue;
            }
            switch (institution.getTyp()) {
                case Krankenhaus:
                case Lieferant:
                case Andere:
                    createPerson(institution);
                    break;
                default:
                    break;
            }
        }
    }

    private void createPerson(InstitutionEntity institution) {
        var personName = faker.name();

        var person = PersonEntity.builder() //
                .institution(institution)
                .vorname(personName.firstName())
                .nachname(personName.lastName()) //
                .username(personName.username())
                .telefon(faker.phoneNumber().phoneNumber()) //
                .build();
        personRepository.save(person);
    }

    private void createKategorien() {
        createKategorie("Behelfs-Maske");
        createKategorie("Desinfektion");
        createKategorie("Hygiene");
        createKategorie("Probenentnahme");
        createKategorie("Schutzkleidung");
        createKategorie("Schutzmasken");
    }

    private void createKategorie(String name) {
        var entity = ArtikelKategorieEntity.builder() //
                .name(name) //
                .build();
        artikelKategorieRepository.save(entity);
    }

    private void createInstitutions() {
        //Krankenhäuser
        createInstitution("institution1", "Paracelsus Klinik München", InstitutionTyp.Krankenhaus);
        createInstitution("institution2", "Arabella-Klinik GmbH", InstitutionTyp.Krankenhaus);
        createInstitution("institution3", "München Klinik Bogenhausen", InstitutionTyp.Krankenhaus);
        createInstitution("institution4", "Städtisches Klinikum München GmbH", InstitutionTyp.Krankenhaus);
        createInstitution("institution5", "Klinik Dr. Schreiber GmbH", InstitutionTyp.Krankenhaus);
        createInstitution("institution6", "Immanuel Krankenhaus Berlin - Standort Buch", InstitutionTyp.Krankenhaus);
        createInstitution("institution7", "Bundeswehrkrankenhaus Berlin", InstitutionTyp.Krankenhaus);
        createInstitution("institution8", "Helios Klinikum Berlin-Buch", InstitutionTyp.Krankenhaus);
        createInstitution("institution9", "Charitee - Universitätsmedizin Berlin", InstitutionTyp.Krankenhaus);
        createInstitution("institution10", "Deutsches Herzzentrum Berlin", InstitutionTyp.Krankenhaus);
        createInstitution("institution11", "DRK Kliniken Berlin Mitte", InstitutionTyp.Krankenhaus);
        createInstitution("institution12", "Wilhelmsburger Krankenhaus Groö-Sand", InstitutionTyp.Krankenhaus);
        createInstitution("institution13", "Universitätsklinikum Hamburg-Eppendorf", InstitutionTyp.Krankenhaus);
        createInstitution("institution14", "Sankt Gertrauden-Krankenhaus GmbH", InstitutionTyp.Krankenhaus);
        createInstitution("institution15", "St.Joseph Krankenhaus", InstitutionTyp.Krankenhaus);
        createInstitution("institution16", "Heinrich Sengelmann Krankenhaus", InstitutionTyp.Krankenhaus);
        createInstitution("institution17", "Krankenhaus Reinbek St. Adolf-Stift GmbH", InstitutionTyp.Krankenhaus);
        createInstitution("institution18", "Asklepios Westklinikum Hamburg GmbH", InstitutionTyp.Krankenhaus);
        createInstitution("institution19", "Elbe Klinikum Buxtehude", InstitutionTyp.Krankenhaus);
        createInstitution("institution20", "Asklepios Klinik Nord, Heidberg", InstitutionTyp.Krankenhaus);
        createInstitution("institution21", "Klinik Charlottenhaus", InstitutionTyp.Krankenhaus);
        createInstitution("institution22", "Diakonie-Klinikum Stuttgart", InstitutionTyp.Krankenhaus);
        createInstitution("institution23", "Krankenhaus vom Roten Kreuz Bad Cannstatt GmbH", InstitutionTyp.Krankenhaus);
        createInstitution("institution24", "Marienhospital Stuttgart", InstitutionTyp.Krankenhaus);

        //Ärzte
        createInstitution("institution25", "Dr. Thoams Meier", InstitutionTyp.Arzt);
        createInstitution("institution26", "Prof. Dr. Hohenstett", InstitutionTyp.Arzt);
        createInstitution("institution27", "Dr. Hans Keller", InstitutionTyp.Arzt);
        createInstitution("institution28", "Gemeinschaftspraxis Jaeger", InstitutionTyp.Arzt);

        //Hersteller
        createInstitution("institution29", "MediTech", InstitutionTyp.Lieferant);
        createInstitution("institution30", "CareTextil", InstitutionTyp.Lieferant);
        createInstitution("institution31", "HopitalServices", InstitutionTyp.Lieferant);

        //Firmen und andere
        createInstitution("institution32", "Gebauedereinigung Stettner", InstitutionTyp.Andere);
        createInstitution("institution33", "Gebauedesanierung Rieger", InstitutionTyp.Andere);
        createInstitution("institution34", "Lackiererei Müller", InstitutionTyp.Andere);
        for (int i = 0; i < 100; i++) {
            String name = faker.company().name();
            createInstitution("other" + i, name, InstitutionTyp.Andere);
        }

        //Privatpersonen
        for (int i = 0; i < 500; i++) {
            String name = faker.name().fullName();
            createInstitution("private" + i, name, InstitutionTyp.Privat);
        }
    }

    private void createInstitution(String key, String name, InstitutionTyp type) {
        var entity = InstitutionEntity.builder() //
                .institutionKey(key) //
                .name(name) //
                .typ(type) //
                .build();
        institutionRepository.save(entity);
    }
}
