package edu.kazakovushka.hospital.repo;

import edu.kazakovushka.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Patient findByNameAndSurname(String name, String surname);
}
