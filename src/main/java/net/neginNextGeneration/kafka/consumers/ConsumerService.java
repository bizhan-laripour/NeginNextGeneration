package net.neginNextGeneration.kafka.consumers;

import com.google.gson.Gson;
import net.neginNextGeneration.dto.DotinDto;
import net.neginNextGeneration.dto.UserDto;
import net.neginNextGeneration.enums.Status;
import net.neginNextGeneration.events.fail.DotinFaiure;
import net.neginNextGeneration.events.fail.UserFailure;
import net.neginNextGeneration.events.success.DotinSucess;
import net.neginNextGeneration.events.success.UserSucess;
import net.neginNextGeneration.dto.KafkaTransferDto;
import net.neginNextGeneration.kafka.producers.ProducerService;
import net.neginNextGeneration.mapper.DotinMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {


    private final UserFailure userFailure;
    private final UserSucess userSucess;
    private final DotinMapper dotinMapper;
    private final DotinSucess dotinSucess;
    private final DotinFaiure dotinFaiure;

    public ConsumerService(UserFailure userFailure, UserSucess userSucess, DotinMapper dotinMapper
            , DotinSucess dotinSucess, DotinFaiure dotinFaiure) {
        this.userFailure = userFailure;
        this.dotinFaiure = dotinFaiure;
        this.dotinSucess = dotinSucess;
        this.userSucess = userSucess;

        this.dotinMapper = dotinMapper;
    }


    @KafkaListener(topics = ProducerService.PROCESS_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void consumeFromProcessTopic(ConsumerRecord<String, KafkaTransferDto> kafka) {
        KafkaTransferDto obj = kafka.value();
        Gson gson = new Gson();
        String t = gson.toJson(obj.getObject());
        UserDto userDto = gson.fromJson(t, UserDto.class);
        KafkaTransferDto kafkaTransferDto = new KafkaTransferDto();
        kafkaTransferDto.setObject(userDto);
        kafkaTransferDto.setName(Status.SENT_TO_DOTIN.name());
        try {
            userSucess.userSuccess(userDto);
        } catch (Exception exception) {
            userFailure.userFailProcess(userDto);
        }
    }

    @KafkaListener(topics = ProducerService.DOTIN_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void consumeFromDotinTopic(ConsumerRecord<String, KafkaTransferDto> kafka) {
        KafkaTransferDto obj = kafka.value();
        Gson gson = new Gson();
        String t = gson.toJson(obj.getObject());
        UserDto userDto = gson.fromJson(t, UserDto.class);
        DotinDto dotinDto = dotinMapper.entityToDto(dotinMapper.UserToDotin(userDto));
        KafkaTransferDto kafkaTransferDto = new KafkaTransferDto();
        kafkaTransferDto.setObject(dotinDto);
        kafkaTransferDto.setName("DOTIN_TOPIC");
        try {
            dotinSucess.dotinSuccess(dotinDto);
        } catch (Exception exception) {
            dotinFaiure.dotinFailureProccess(dotinDto);
        }

    }
}
