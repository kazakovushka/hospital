package edu.kazakovushka.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String phone;


    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<IllnessHistory> illnessHistories = new HashSet<>();

    private LocalDateTime modifiedTimestamp;
    private int updatesCount;

    public Patient(String name, String surname, String phone, LocalDateTime modifiedTimestamp, int updatesCount) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.modifiedTimestamp = modifiedTimestamp;
        this.updatesCount = updatesCount;
    }

    public Patient() {
    }
}
