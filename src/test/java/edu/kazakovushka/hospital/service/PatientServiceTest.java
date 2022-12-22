package edu.kazakovushka.hospital.service;

import edu.kazakovushka.hospital.model.IllnessHistory;
import edu.kazakovushka.hospital.model.Patient;
import edu.kazakovushka.hospital.repo.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
class PatientServiceTest {

    @Autowired
    PatientService patientService;

    @Autowired
    PatientRepository patientRepository;

    private final String name = "Petr";
    private final String surname = "Semenov";

    Patient patient = getTestPatient();


    @Test
    void addPatient() {
        System.out.println(patientService);
        var patient = getTestPatient();
        patientService.addPatient(patient);
        var patientInDb = patientRepository.findByNameAndSurname(name, surname);
        assertNotNull(patientInDb);
    }

    private Patient getTestPatient() {
        return new Patient(name, surname, "764-32-54", LocalDateTime.now(), 0);
    }

    @Test
    void findPatientById() {
        var foundPatient = patientService.findPatientById(patient.getId());
        assertNotNull(foundPatient);
    }


    @Test
    void findPatientByNameSurname() {
        var patientInDb = patientService.findPatientByNameSurname(name, surname);
        assertNotNull(patientInDb);
    }

    @Test
    void addIllnessToPatient() {
        var illnessHistory = new IllnessHistory();
        illnessHistory.setStartDate(LocalDate.now().minusDays(1));
        illnessHistory.setEndDate(LocalDate.now());
        illnessHistory.setDescription("test illness hist");
        var savedPatient = patientService.addIllnessToPatient(patient.getId(), illnessHistory);

        assertNotNull(savedPatient);
        assertEquals(1, savedPatient.getIllnessHistories().size());
        var illnessSaved = savedPatient.getIllnessHistories().stream().findFirst().get();

        System.out.println("illnessSaved=" + illnessSaved);
        assertEquals(patient.getId(), illnessSaved.getPatient().getId());
        assertEquals(LocalDate.now().minusDays(1), illnessSaved.getStartDate());
        assertEquals(LocalDate.now(), illnessSaved.getEndDate());
        assertEquals("test illness hist", illnessSaved.getDescription());
    }



    @Test
    void testSave() {
        assertNotNull(patient.getId());
    }

    @BeforeEach
    void removeTestPatientIfExist() {
        patient = patientRepository.save(patient);
        System.out.println("-------Test has been Initialized");
    }

    @AfterEach
    void tearDown() {
        System.out.println("------Shutting down tests");
        patientRepository.deleteById(patient.getId());
    }
}