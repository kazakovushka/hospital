package edu.kazakovushka.hospital.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Illness {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String code;
    private String description;
    //@OneToMany(mappedBy = "illness", fetch = FetchType.LAZY)
    // private Set<IllnessHistory> illnessHistories;

    public Illness() {
    }
}
