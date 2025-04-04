package com.Agri.AgriBack.Command.dto;

import com.Agri.AgriBack.Command.entity.Employee;
import com.Agri.AgriBack.Command.entity.endDevice;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EndDeviceProducer {
    private final KafkaTemplate<String, endDevice> kafkaTemplate;
    private final KafkaTemplate<String, Long> kafkaTemplateLong; // Template pour les IDs

    public EndDeviceProducer(KafkaTemplate<String, endDevice> kafkaTemplate, KafkaTemplate<String, Long> kafkaTemplateLong) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateLong = kafkaTemplateLong;
    }

    public void createEndDeviceEvent(endDevice device) {
        kafkaTemplate.send("EndDevice_created", device);
    }

    public void updateEndDeviceEvent(endDevice device) {
        kafkaTemplate.send("EndDevice_updated", device);
    }

    public void deleteEndDeviceEvent(Long deviceId) {
        kafkaTemplateLong.send("EndDevice_deleted", deviceId);
    }
}
