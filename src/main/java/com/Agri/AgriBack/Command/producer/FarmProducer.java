package com.Agri.AgriBack.Command.producer;

import com.Agri.AgriBack.DTO.ActuatorDTO;
import com.Agri.AgriBack.DTO.FarmDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FarmProducer {
    private final KafkaTemplate<String, FarmDTO> kafkaTemplate;
    private final KafkaTemplate<String, Long> kafkaTemplateLong;

    public FarmProducer(KafkaTemplate<String, FarmDTO> kafkaTemplate, KafkaTemplate<String, Long> kafkaTemplateLong) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplateLong = kafkaTemplateLong;
    }
    public void createFarmEvent(FarmDTO farm) {
        kafkaTemplate.send("Farm_created", farm);
    }

    public void updateFarmEvent(FarmDTO farm) {
        kafkaTemplate.send("Farm_updated", farm);
    }

    public void deleteFarmEvent(Long farmId) {
        kafkaTemplateLong.send("Farm_deleted", farmId);
    }
}
