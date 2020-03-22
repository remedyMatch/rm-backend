package io.remedymatch.dbinit;

import com.github.javafaker.Faker;
import io.remedymatch.artikel.domain.ArtikelEntity;
import io.remedymatch.artikel.domain.ArtikelJpaRepository;
import io.remedymatch.artikel.domain.ArtikelKategorieEntity;
import io.remedymatch.artikel.domain.ArtikelKategorieRepository;
import io.remedymatch.bedarf.domain.BedarfEntity;
import io.remedymatch.bedarf.domain.BedarfRepository;
import io.remedymatch.institution.domain.InstitutionEntity;
import io.remedymatch.institution.domain.InstitutionRepository;
import io.remedymatch.institution.domain.InstitutionTyp;
import io.remedymatch.person.domain.PersonEntity;
import io.remedymatch.person.domain.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

/**
 * TODO Test-Code nicht f�r Produktion
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
            createBedarfsListe();
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Fehler beim Erstellen des initialen Datenbestandes", ex);
        }
    }

    public void createBedarf(double anzahl, ArtikelEntity artikel, InstitutionEntity institution) {
        var bedarf = BedarfEntity.builder() //
                .anzahl(anzahl) //
                .artikel(artikel)
                .institution(institution) //
                .build();
        bedarfRepository.save(bedarf);
    }

    public void createBedarfsListe() {
        var artikel = createBedarfArtikel("Chemielabor Kittel", "Gebrauchter Kittel aus dem Chemielabor des KIT, 100% Baumwolle", artikelKategorieRepository.findByName("Kittel One-Size").get());
        createBedarf(15, artikel, institutionRepository.findByInstitutionKey("institution2"));

        artikel = createBedarfArtikel("Schutzmasken", "Schutzmasken FFP2 Zustand neu", artikelKategorieRepository.findByName("Masken FFP2").get());
        createBedarf(6000, artikel, institutionRepository.findByInstitutionKey("institution12"));

        artikel = createBedarfArtikel("Schutzmasken", "Schutzmasken FFP2 Zustand neu", artikelKategorieRepository.findByName("Masken FFP2").get());
        createBedarf(5000, artikel, institutionRepository.findByInstitutionKey("institution13"));

        artikel = createBedarfArtikel("Schutzmasken", "Schutzmasken FFP2", artikelKategorieRepository.findByName("Masken FFP2").get());
        createBedarf(2500, artikel, institutionRepository.findByInstitutionKey("institution14"));

        artikel = createBedarfArtikel("Schutzmasken", "Schutzmasken FFP3 Zustand originalverpackt", artikelKategorieRepository.findByName("Masken FFP3").get());
        createBedarf(12000, artikel, institutionRepository.findByInstitutionKey("institution11"));

        artikel = createBedarfArtikel("Schutzmasken", "Schutzmasken FFP3 neu", artikelKategorieRepository.findByName("Masken FFP3").get());
        createBedarf(6000, artikel, institutionRepository.findByInstitutionKey("institution18"));

        artikel = createBedarfArtikel("Schutzmasken", "FFP3", artikelKategorieRepository.findByName("Masken FFP3").get());
        createBedarf(6000, artikel, institutionRepository.findByInstitutionKey("institution9"));

        artikel = createBedarfArtikel("Handdesinfektion", "Kanister für Desifektion Corona", artikelKategorieRepository.findByName("Handdesinfektion").get());
        createBedarf(20, artikel, institutionRepository.findByInstitutionKey("institution12"));

        artikel = createBedarfArtikel("Handschuhe", "Größe S im Gebinde a 100 Stück", artikelKategorieRepository.findByName("Handschuhe S").get());
        createBedarf(150, artikel, institutionRepository.findByInstitutionKey("institution13"));

        artikel = createBedarfArtikel("Handschuhe", "Größe L im Gebinde a 500 Stück", artikelKategorieRepository.findByName("Handschuhe L").get());
        createBedarf(80, artikel, institutionRepository.findByInstitutionKey("institution16"));

        artikel = createBedarfArtikel("Handschuhe", "Größe XL im Gebinde a 200 Stück", artikelKategorieRepository.findByName("Handschuhe XL").get());
        createBedarf(350, artikel, institutionRepository.findByInstitutionKey("institution16"));

        artikel = createBedarfArtikel("Handschuhe", "Größe XL im Gebinde a 200 Stück", artikelKategorieRepository.findByName("Handschuhe XL").get());
        createBedarf(250, artikel, institutionRepository.findByInstitutionKey("institution17"));

        artikel = createBedarfArtikel("Schutzhandschuhe", "Einzeln", artikelKategorieRepository.findByName("Handschuhe L").get());
        createBedarf(3000, artikel, institutionRepository.findByInstitutionKey("institution14"));

        artikel = createBedarfArtikel("Brillen", "neu", artikelKategorieRepository.findByName("Brillen").get());
        createBedarf(1500, artikel, institutionRepository.findByInstitutionKey("institution15"));

        artikel = createBedarfArtikel("Infusionsbesteck", "neu", artikelKategorieRepository.findByName("Infusionsbesteck").get());
        createBedarf(200, artikel, institutionRepository.findByInstitutionKey("institution16"));

        artikel = createBedarfArtikel("Virostatika", "neu", artikelKategorieRepository.findByName("Virostatika").get());
        createBedarf(250, artikel, institutionRepository.findByInstitutionKey("institution17"));
    }

    private ArtikelEntity createBedarfArtikel(String name, String beschreibung, ArtikelKategorieEntity kategorie) {
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
                case HOSPITAL:
                case SUPPLIER:
                case OTHER:
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
        //Krankenh�user
        createInstitution("institution1", "Paracelsus Klinik München", InstitutionTyp.HOSPITAL);
        createInstitution("institution2", "Arabella-Klinik GmbH", InstitutionTyp.HOSPITAL);
        createInstitution("institution3", "München Klinik Bogenhausen", InstitutionTyp.HOSPITAL);
        createInstitution("institution4", "Städtisches Klinikum München GmbH", InstitutionTyp.HOSPITAL);
        createInstitution("institution5", "Klinik Dr. Schreiber GmbH", InstitutionTyp.HOSPITAL);
        createInstitution("institution6", "Immanuel Krankenhaus Berlin - Standort Buch", InstitutionTyp.HOSPITAL);
        createInstitution("institution7", "Bundeswehrkrankenhaus Berlin", InstitutionTyp.HOSPITAL);
        createInstitution("institution8", "Helios Klinikum Berlin-Buch", InstitutionTyp.HOSPITAL);
        createInstitution("institution9", "Charite - Universitütsmedizin Berlin", InstitutionTyp.HOSPITAL);
        createInstitution("institution10", "Deutsches Herzzentrum Berlin", InstitutionTyp.HOSPITAL);
        createInstitution("institution11", "DRK Kliniken Berlin Mitte", InstitutionTyp.HOSPITAL);
        createInstitution("institution12", "Wilhelmsburger Krankenhaus Groß-Sand", InstitutionTyp.HOSPITAL);
        createInstitution("institution13", "Universitütsklinikum Hamburg-Eppendorf", InstitutionTyp.HOSPITAL);
        createInstitution("institution14", "Sankt Gertrauden-Krankenhaus GmbH", InstitutionTyp.HOSPITAL);
        createInstitution("institution15", "St.Joseph Krankenhaus", InstitutionTyp.HOSPITAL);
        createInstitution("institution16", "Heinrich Sengelmann Krankenhaus", InstitutionTyp.HOSPITAL);
        createInstitution("institution17", "Krankenhaus Reinbek St. Adolf-Stift GmbH", InstitutionTyp.HOSPITAL);
        createInstitution("institution18", "Asklepios Westklinikum Hamburg GmbH", InstitutionTyp.HOSPITAL);
        createInstitution("institution19", "Elbe Klinikum Buxtehude", InstitutionTyp.HOSPITAL);
        createInstitution("institution20", "Asklepios Klinik Nord, Heidberg", InstitutionTyp.HOSPITAL);
        createInstitution("institution21", "Klinik Charlottenhaus", InstitutionTyp.HOSPITAL);
        createInstitution("institution22", "Diakonie-Klinikum Stuttgart", InstitutionTyp.HOSPITAL);
        createInstitution("institution23", "Krankenhaus vom Roten Kreuz Bad Cannstatt GmbH", InstitutionTyp.HOSPITAL);
        createInstitution("institution24", "Marienhospital Stuttgart", InstitutionTyp.HOSPITAL);

        //�rzte
        createInstitution("institution25", "Dr. Thoams Meier", InstitutionTyp.DOCTOR);
        createInstitution("institution26", "Prof. Dr. Hohenstett", InstitutionTyp.DOCTOR);
        createInstitution("institution27", "Dr. Hans Keller", InstitutionTyp.DOCTOR);
        createInstitution("institution28", "Gemeinschaftspraxis Jaeger", InstitutionTyp.DOCTOR);

        //Hersteller
        createInstitution("institution29", "MediTech", InstitutionTyp.SUPPLIER);
        createInstitution("institution30", "CareTextil", InstitutionTyp.SUPPLIER);
        createInstitution("institution31", "HopitalServices", InstitutionTyp.SUPPLIER);

        //Firmen und andere
        createInstitution("institution32", "Gebauedereinigung Stettner", InstitutionTyp.OTHER);
        createInstitution("institution33", "Gebauedesanierung Rieger", InstitutionTyp.OTHER);
        createInstitution("institution34", "Lackiererei Müller", InstitutionTyp.OTHER);
        for (int i = 0; i < 100; i++) {
            String name = faker.company().name();
            createInstitution("other" + i, name, InstitutionTyp.OTHER);
        }

        //Privatpersonen
        for (int i = 0; i < 500; i++) {
            String name = faker.name().fullName();
            createInstitution("private" + i, name, InstitutionTyp.PRIVATE);
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
