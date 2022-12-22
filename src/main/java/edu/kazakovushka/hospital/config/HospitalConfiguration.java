package edu.kazakovushka.hospital.config;

import edu.kazakovushka.hospital.repo.PatientRepository;
import edu.kazakovushka.hospital.service.PatientService;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HospitalConfiguration {

    @Bean
    public DataSource getDataSource() {
        var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/postgres");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("docker");
        return dataSourceBuilder.build();
    }

    @Bean
    public PatientService patientService(PatientRepository repository){
        return new PatientService(repository);
    }


}
