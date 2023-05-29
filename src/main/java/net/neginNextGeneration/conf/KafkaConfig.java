package net.neginNextGeneration.conf;

import net.neginNextGeneration.dto.KafkaTransferDto;
import net.neginNextGeneration.kafka.producers.ProducerService;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {


    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Value(value = "${spring.kafka.consumer.group-id}")
    private String groupId;


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaTransferDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaTransferDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


    public ConsumerFactory<String, KafkaTransferDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        JsonDeserializer<KafkaTransferDto> deserializer = new JsonDeserializer<>(KafkaTransferDto.class , false);
        deserializer.addTrustedPackages("*");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }



    @Bean
    public ProducerFactory<String, KafkaTransferDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, KafkaTransferDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic topicProgress() {
        return TopicBuilder.name(ProducerService.PROCESS_TOPIC).build();
    }

    @Bean
    public NewTopic topicDotin() {
        return TopicBuilder.name(ProducerService.DOTIN_TOPIC).build();
    }
}
