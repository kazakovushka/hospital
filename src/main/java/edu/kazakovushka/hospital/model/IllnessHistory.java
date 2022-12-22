package edu.kazakovushka.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class IllnessHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
   // @ManyToOne
    //@JoinColumn(name = "illness_id")
    //private Illness illness;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;


    public IllnessHistory() {
    }

    @Override
    public String toString() {
        return "IllnessHistory{" +
            "id=" + id +
            ", patient id=" + patient.getId() +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", description='" + description + '\'' +
            '}';
    }
}
