package com.Agri.AgriBack.Command.producer;

import com.Agri.AgriBack.Command.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeProducer {
    private final KafkaTemplate<String, Employee> kafkaTemplate;

    public void createEmployeeEvent(Employee employee) {
        kafkaTemplate.send("Employee_created", employee);
    }

    public void updateEmployeeEvent(Employee employee) {
        kafkaTemplate.send("Employee_updated", employee);
    }

    public EmployeeProducer(KafkaTemplate<String, Employee> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
