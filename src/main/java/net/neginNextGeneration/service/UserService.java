package net.neginNextGeneration.service;


import jakarta.transaction.Transactional;
import net.neginNextGeneration.dto.UserDto;
import net.neginNextGeneration.enums.Status;
import net.neginNextGeneration.exception.CustomException;
import net.neginNextGeneration.dto.KafkaTransferDto;
import net.neginNextGeneration.kafka.producers.ProducerService;
import net.neginNextGeneration.mapper.UserMapper;
import net.neginNextGeneration.repository.UserRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static net.neginNextGeneration.kafka.producers.ProducerService.DOTIN_TOPIC;
import static net.neginNextGeneration.kafka.producers.ProducerService.PROCESS_TOPIC;


@Service
public class UserService {


    private final ProducerService producerService;

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Autowired
    public UserService(ProducerService producerService, UserRepository userRepository, UserMapper userMapper) {
        this.producerService = producerService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;

    }

    public void sendMessage(String topic, UserDto userDto) {
        KafkaTransferDto kafkaTransferDto = new KafkaTransferDto();
        kafkaTransferDto.setObject(userDto);
        kafkaTransferDto.setName(Status.PROCESS.name());
        producerService.sendMessage(topic, kafkaTransferDto);
    }

    public UserDto findByTrackId(String trackId) {
        if (userRepository.findByTrackingId(trackId).isPresent())
            return userMapper.entityToDto(userRepository.findByTrackingId(trackId).get());
        else return null;
    }

    public void saveAll(List<UserDto> kafka) {
        if (!kafka.isEmpty())
            userRepository.saveAll(userMapper.dtosToEntities(kafka));
    }

    public void save(UserDto userDto) {
        userRepository.save(userMapper.dtoToEntity(userDto));
    }

    public String processFile(MultipartFile multipartFile) throws IOException {
        return processExcelAndSaveToDB(multipartFile);
    }

    /**
     * save all file elements in to the database
     *
     * @param reapExcelDataFile
     * @return
     * @throws IOException
     */
    @Transactional
    private String processExcelAndSaveToDB(MultipartFile reapExcelDataFile) throws IOException {
        List<UserDto> kafkaList = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        IntStream.range(1, worksheet.getPhysicalNumberOfRows()).forEach(i -> {
            UserDto userDto = new UserDto();
            XSSFRow row = worksheet.getRow(i);
            userDto.setName(row.getCell(0).getStringCellValue());
            userDto.setLastName(row.getCell(1).getStringCellValue());
            userDto.setTrackId(String.valueOf(row.getCell(2).getNumericCellValue()));
            userDto.setStatus(Status.PROCESS);
            kafkaList.add(userDto);
        });
        try {
            saveAll(kafkaList);
        } catch (Exception ex) {
            throw new CustomException(506, "something went wrong");
        }
        kafkaList.forEach(a -> sendMessage(PROCESS_TOPIC , a));
        return "message send to Dotin api";
    }

    @Transactional
    public void sendToDotinTopic(UserDto userDto) {
    KafkaTransferDto kafkaTransferDto = new KafkaTransferDto();
    kafkaTransferDto.setName(Status.SUCCESS.name());
    kafkaTransferDto.setObject(userDto);
    producerService.sendMessage(DOTIN_TOPIC , kafkaTransferDto);

    }

}
