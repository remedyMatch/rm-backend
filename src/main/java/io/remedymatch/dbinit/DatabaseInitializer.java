package io.remedymatch.dbinit;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;

import io.remedymatch.angebot.domain.AngebotEntity;
import io.remedymatch.angebot.infrastructure.AngebotJpaRepository;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.artikel.domain.ArtikelJpaRepository;
import io.remedymatch.artikel.domain.ArtikelKategorieEntity;
import io.remedymatch.artikel.domain.ArtikelKategorieRepository;
import io.remedymatch.bedarf.domain.BedarfRepository;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionRepository;
import io.remedymatch.institution.domain.InstitutionStandortEntity;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.person.domain.PersonEntity;
import io.remedymatch.person.domain.PersonRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO Test-Code nicht für Produktion
 */
@Component
@Profile("test")
@Slf4j
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BedarfRepository bedarfRepository;

    @Autowired
    private AngebotJpaRepository angebotRepository;

    @Autowired
    private ArtikelJpaRepository artikelRepository;

    @Autowired
    private ArtikelKategorieRepository artikelKategorieRepository;

    @Autowired
    private InstitutionRepository institutionRepository;

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
            // createAngebotsListe();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Fehler beim Erstellen des initialen Datenbestandes", ex);
        }
    }

//    private void createAngebotsListe() {
//
//
//
//        var artikel = createArtikel("Schutzmasken", "Schutzmasken FFP1", artikelKategorieRepository.findByName("Masken FFP1").get());
//        var institution = institutionRepository.findByInstitutionKey("private100");
//        createAngebot("Schutzmasken", "Köln", false, false, false, 20, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken (aus Eigenbedarf)", "Schutzmasken FFP2", artikelKategorieRepository.findByName("Masken FFP2").get());
//        institution = institutionRepository.findByInstitutionKey("private101");
//        createAngebot("Schutzmasken", "Augsburg", false, false, false, 30, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken von privat", "Schutzmasken FFP3", artikelKategorieRepository.findByName("Masken FFP3").get());
//        institution = institutionRepository.findByInstitutionKey("private101");
//        createAngebot("Schutzmasken", "Dresden", false, false, false, 50, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken privat", "Schutzmasken FFP3", artikelKategorieRepository.findByName("Masken FFP3").get());
//        institution = institutionRepository.findByInstitutionKey("private102");
//        createAngebot("Schutzmasken", "Flensburg", false, false, false, 50, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP1", artikelKategorieRepository.findByName("Masken FFP1").get());
//        institution = institutionRepository.findByInstitutionKey("private110");
//        createAngebot("Schutzmasken", "Köln", false, false, false, 20, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP2 neu", artikelKategorieRepository.findByName("Masken FFP2").get());
//        institution = institutionRepository.findByInstitutionKey("private120");
//        createAngebot("Schutzmasken", "München", true, false, true, 50, LocalDate.now().plusYears(6).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP1 aus privatem Bestand", artikelKategorieRepository.findByName("Masken FFP1").get());
//        institution = institutionRepository.findByInstitutionKey("private130");
//        createAngebot("Schutzmasken", "Köln", true, false, true, 20, LocalDate.now().plusYears(3).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP3 neu", artikelKategorieRepository.findByName("Masken FFP3").get());
//        institution = institutionRepository.findByInstitutionKey("private140");
//        createAngebot("Schutzmasken", "Hamburg", true, false, true, 120, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP2 Restbestand", artikelKategorieRepository.findByName("Masken FFP2").get());
//        institution = institutionRepository.findByInstitutionKey("private150");
//        createAngebot("Schutzmasken", "Hamburg", true, false, true, 120, LocalDate.now().plusYears(3).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzmasken", "Schutzmasken FFP2 von Privat", artikelKategorieRepository.findByName("Masken FFP2").get());
//        institution = institutionRepository.findByInstitutionKey("private160");
//        createAngebot("Schutzmasken", "Berlin", true, false, true, 40, LocalDate.now().plusYears(3).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Brillen", "Arbeitsschutzbrille", artikelKategorieRepository.findByName("Brillen").get());
//        institution = institutionRepository.findByInstitutionKey("private170");
//        createAngebot("Brillen", "Stuttgart", true, false, true, 20, LocalDate.now().plusYears(3).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Brillen", "Arbeitsschutzbrille unbenutzt", artikelKategorieRepository.findByName("Brillen").get());
//        institution = institutionRepository.findByInstitutionKey("private180");
//        createAngebot("Brillen", "München", true, false, true, 15, LocalDate.now().plusYears(1).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Brillen", "Arbeitsschutzbrille (neu)", artikelKategorieRepository.findByName("Brillen").get());
//        institution = institutionRepository.findByInstitutionKey("private190");
//        createAngebot("Schutzbrillen", "Bonn", true, false, true, 15, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Handdesinfektion", "Kanister 50Liter (neu)", artikelKategorieRepository.findByName("Handdesinfektion").get());
//        institution = institutionRepository.findByInstitutionKey("private200");
//        createAngebot("Handdesinfektion", "Karlsruhe", true, false, true, 2, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Handdesinfektion", "Kanister 10Liter unbenutzt", artikelKategorieRepository.findByName("Handdesinfektion").get());
//        institution = institutionRepository.findByInstitutionKey("private210");
//        createAngebot("Handdesinfektion von private", "Augsburg", true, false, true, 3, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung", "unbenutzt", artikelKategorieRepository.findByName("Overall Größe M").get());
//        institution = institutionRepository.findByInstitutionKey("private220");
//        createAngebot("Overall", "Augsburg", true, false, true, 3, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung", "1 Karton mit 100 Stück unbenutzt", artikelKategorieRepository.findByName("Overall Größe L").get());
//        institution = institutionRepository.findByInstitutionKey("private220");
//        createAngebot("Overall", "Augsburg", true, false, true, 100, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung", "unbenutzt/neu", artikelKategorieRepository.findByName("Overall Größe L").get());
//        institution = institutionRepository.findByInstitutionKey("private220");
//        createAngebot("Overall", "Augsburg", true, false, true, 500, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung (neu)", "neu/steril", artikelKategorieRepository.findByName("Overall Größe XL").get());
//        institution = institutionRepository.findByInstitutionKey("private220");
//        createAngebot("mehere Overalls", "Berlin", true, false, true, 100, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung (neu)", "neu/steril", artikelKategorieRepository.findByName("Overall Größe XL").get());
//        institution = institutionRepository.findByInstitutionKey("other10");
//        createAngebot("Schutzbekleidung", "Frankfurt", true, false, true, 5000, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung (neu)", "steril", artikelKategorieRepository.findByName("Overall Größe L").get());
//        institution = institutionRepository.findByInstitutionKey("institution8");
//        createAngebot("Schutzbekleidung aus Krankenhausbestand", "Berlin", true, false, true, 6000, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung (neu)", "steril", artikelKategorieRepository.findByName("Overall Größe M").get());
//        institution = institutionRepository.findByInstitutionKey("other8");
//        createAngebot("Schutzbekleidung aus Herstellung", "Hamburg", true, false, true, 20000, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung (auf Lager)", "steril", artikelKategorieRepository.findByName("Overall Größe L").get());
//        institution = institutionRepository.findByInstitutionKey("other8");
//        createAngebot("Schutzbekleidung aus Herstellung", "Hamburg", true, false, true, 15000, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung (auf Lager)", "steril", artikelKategorieRepository.findByName("Overall Größe XL").get());
//        institution = institutionRepository.findByInstitutionKey("other8");
//        createAngebot("Schutzbekleidung aus Herstellung", "Hamburg", true, false, true, 30000, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung", "steril", artikelKategorieRepository.findByName("Overall Größe XL").get());
//        institution = institutionRepository.findByInstitutionKey("institution34");
//        createAngebot("Schutzbekleidung im eigenen Bestand", "Düsseldorf", true, false, true, 500, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//        artikel = createArtikel("Schutzbekleidung", "steril", artikelKategorieRepository.findByName("Overall Größe L").get());
//        institution = institutionRepository.findByInstitutionKey("institution34");
//        createAngebot("Schutzbekleidung im eigenen Bestand", "Düsseldorf", true, false, true, 200, LocalDate.now().plusYears(2).atStartOfDay(), artikel, institution);
//
//
//    }

    private AngebotEntity createAngebot(String kommentar, InstitutionStandortEntity standort, boolean medizinisch, boolean steril, boolean originalverpackt, double anzahl, LocalDateTime haltbarkeit, ArtikelEntity artikel, InstitutionEntity institution) {
        var angebot = AngebotEntity.builder() //
                .anzahl(anzahl) //
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

    private ArtikelEntity createArtikel(String name, String beschreibung, ArtikelKategorieEntity kategorie) {
        var entity = ArtikelEntity.builder() //
                .beschreibung(beschreibung) //
                .hersteller(faker.company().name()) //
                .name(name) //
                .ean((faker.number().randomNumber(13, true) + "")) //
                .artikelKategorie(kategorie) //
                .build();
        artikelRepository.save(entity);
        return entity;
    }

    private void createPersonen() {
        for (InstitutionEntity institution : institutionRepository.findAll()) {
            switch (institution.getTyp()) {
                case Krankenhaus:
                case Lieferant:
                case Andere:
                    createPerson(institution);
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
        createKategorie("Masken FFP1");
        createKategorie("Masken FFP2");
        createKategorie("Masken FFP3");

        createKategorie("Overall Größe S");
        createKategorie("Overall Größe M");
        createKategorie("Overall Größe L");
        createKategorie("Overall Größe XL");

        createKategorie("Kittel One-Size");

        createKategorie("Brillen");

        createKategorie("Handschuhe S");
        createKategorie("Handschuhe M");
        createKategorie("Handschuhe L");
        createKategorie("Handschuhe XL");

        createKategorie("Sterillium (Virugard)");
        createKategorie("Handdesinfektion");
        createKategorie("Blutentnahme Set");
        createKategorie("Schlauch Sauerstoffabgabe");
        createKategorie("Venenverweilkanülen");
        createKategorie("Infusionsbesteck");
        createKategorie("ZVK");
        createKategorie("arterielle Kanülen");

        createKategorie("Narkosemittel");
        createKategorie("Schmerzmittel");
        createKategorie("Virostatika");
        createKategorie("Infusion");
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
