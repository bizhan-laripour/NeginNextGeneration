package net.neginNextGeneration.kafka.producers;

import net.neginNextGeneration.dto.KafkaTransferDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class ProducerService {


    private final KafkaTemplate<String, KafkaTransferDto> kafkaTemplate;

    public static final String PROCESS_TOPIC= "PROCESS_TOPIC";
    public static final String DOTIN_TOPIC = "DOTIN_TOPIC";

    @Autowired
    public ProducerService(KafkaTemplate<String, KafkaTransferDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, KafkaTransferDto kafkaTransferDto) {
        kafkaTemplate.send(topic, kafkaTransferDto);

    }


}
