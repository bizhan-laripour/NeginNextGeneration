//package net.neginNextGeneration.service;
//
//
//import jakarta.transaction.Transactional;
//import net.neginNextGeneration.enums.Status;
//import net.neginNextGeneration.exception.CustomException;
//import net.neginNextGeneration.dto.UserDto;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//import static net.neginNextGeneration.kafka.producers.ProducerService.PROCESS_TOPIC;
//
//@Component
//public class UtilityService {
//
//
//
//    @Autowired
//    public UtilityService(UserService userService){
//        this.userService = userService;
//    }
//
//
//
//
//    @Transactional
//    public String processFile(MultipartFile multipartFile) throws IOException {
//        List<UserDto> userDtos = processExcelAndSaveToDB(multipartFile);
//        userDtos.forEach(a -> userService.sendMessage(PROCESS_TOPIC , a));
//        return "task done";
//    }
//}
