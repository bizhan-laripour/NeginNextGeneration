package net.neginNextGeneration.controller;


import net.neginNextGeneration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @RequestMapping(value = "/get-file" , method = RequestMethod.POST)
    public ResponseEntity<?> processFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
       return new ResponseEntity<>( userService.processFile(multipartFile) , HttpStatus.OK);
    }

    @RequestMapping(value = "/get")
    public ResponseEntity<?> get(){
        return new ResponseEntity<>("hello" , HttpStatus.OK);
    }
}
