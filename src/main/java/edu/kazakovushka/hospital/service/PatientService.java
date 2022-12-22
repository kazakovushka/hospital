package edu.kazakovushka.hospital.service;

import edu.kazakovushka.hospital.model.IllnessHistory;
import edu.kazakovushka.hospital.model.Patient;
import edu.kazakovushka.hospital.repo.PatientRepository;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Transactional
    public Patient addPatient(Patient patient) {
        var id = patient.getId();
        if (nonNull(id)) {
            var patientWithId = findPatientById(id);
            if (nonNull(patientWithId)) {
                log.error("patient with id " + id + " already exists");
                return null;
            }
        }
        patient.setModifiedTimestamp(LocalDateTime.now());
        return patientRepository.save(patient);
    }

    @Transactional
    public Patient findPatientById(long id) {
        return patientRepository.findById(id).orElse(null);
    }


    @Transactional
    public Patient findPatientByNameSurname(String name, String surname) {
        return patientRepository.findByNameAndSurname(name, surname);
    }

    @Transactional
    public Patient addIllnessToPatient(Long patientId, IllnessHistory illnessHistory) {
        var optPatient = patientRepository.findById(patientId);
        optPatient.ifPresent(patient -> {
            addIlnessToPatient(patient, illnessHistory);
        });
        return optPatient.orElse(null);
    }

    public void addIlnessToPatient(Patient patient, IllnessHistory illnessHistory) {
        var histories = patient.getIllnessHistories();
        var sameHistory = histories.stream().filter(
            illnessHistoryInDb -> illnessHistoryInDb.getStartDate().isEqual(illnessHistory.getStartDate()) &&
                illnessHistoryInDb.getEndDate().isEqual(illnessHistory.getEndDate())).findFirst();
        if (sameHistory.isPresent()) {
            throw new IllegalStateException("this illness already saved in DB");
        } else {
            patient.getIllnessHistories().add(illnessHistory);
            illnessHistory.setPatient(patient);
            System.out.println("add illness to patient set" + illnessHistory);
        }
    }

    public Patient addIllnessToPatient(IllnessHistory illnessHistory) {
        var patient = illnessHistory.getPatient();
        var patientInDb = patientRepository.findById(patient.getId());
        if (patientInDb.isPresent()) {
            var histories = patientInDb.get().getIllnessHistories();
            var sameHistory = histories.stream().filter(
                illnessHistoryInDb -> illnessHistoryInDb.getStartDate().isEqual(illnessHistory.getStartDate()) &&
                    illnessHistoryInDb.getEndDate().isEqual(illnessHistory.getEndDate())).findFirst();
            if (sameHistory.isPresent()) {
                throw new IllegalStateException("this illness already saved in DB");
            } else {
                patientInDb.get().getIllnessHistories().add(illnessHistory);
                System.out.println("add illness to patient set" + illnessHistory);
                return patientRepository.save(patientInDb.get());
            }
        }
        return null;
    }
}
