package edu.kazakovushka.hospital.controller;

import edu.kazakovushka.hospital.model.IllnessHistory;
import edu.kazakovushka.hospital.model.Patient;
import edu.kazakovushka.hospital.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("patients")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping()
    public ResponseEntity<Patient> getPatientByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        return new ResponseEntity<>(patientService.findPatientByNameSurname(name, surname), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(patientService.findPatientById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        return new ResponseEntity<>(patientService.addPatient(patient), HttpStatus.OK);
    }

    @PostMapping("/{id}/illnesses")
    public ResponseEntity<Patient> addIllnessToPatient(@RequestBody IllnessHistory illnessHistory){
        return new ResponseEntity<>(patientService.addIllnessToPatient(illnessHistory), HttpStatus.OK);
    }
}
