package edi;

import edi.repository.file.ComputerRepairRequestFileRepository;
import edi.repository.file.ComputerRepairedFormFileRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import edi.repository.ComputerRepairRequestRepository;
import edi.repository.ComputerRepairedFormRepository;
import edi.repository.jdbc.ComputerRepairRequestJdbcRepository;
import edi.repository.jdbc.ComputerRepairedFormJdbcRepository;
import edi.services.ComputerRepairServices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepairShopConfig {
    @Bean
    Properties getProps() {
        Properties props = new Properties();
        try {
            System.out.println("Searching for bd.config...");
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.err.println("bd.config not found");
        }

        return props;
    }

    @Bean
    ComputerRepairRequestRepository requestsRepo(){
//        return new ComputerRepairRequestJdbcRepository(getProps());
        return new ComputerRepairRequestFileRepository("ComputerRequests.txt");
    }

    @Bean
    ComputerRepairedFormRepository formsRepo(){
//       return new ComputerRepairedFormJdbcRepository(getProps());
       return new ComputerRepairedFormFileRepository("RepairedForms.txt", requestsRepo());
    }

    @Bean
    ComputerRepairServices services(){
       return new ComputerRepairServices(requestsRepo(), formsRepo());
    }

}
