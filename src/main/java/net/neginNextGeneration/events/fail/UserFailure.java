package net.neginNextGeneration.events.fail;

import net.neginNextGeneration.annotations.Failure;
import net.neginNextGeneration.dto.UserDto;
import net.neginNextGeneration.enums.Status;
import net.neginNextGeneration.service.UserService;

@Failure
public class UserFailure {

    private final UserService userService;

    public UserFailure(UserService userService){
        this.userService = userService;
    }

    public void userFailProcess(UserDto userDto){
        UserDto user = userService.findByTrackId(userDto.getTrackId());
        if(user != null){
            userDto.setStatus(Status.FAIL);
            userService.save(userDto);
        }

    }
}
