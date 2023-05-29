package net.neginNextGeneration.events.success;

import net.neginNextGeneration.annotations.Success;
import net.neginNextGeneration.dto.UserDto;
import net.neginNextGeneration.enums.Status;
import net.neginNextGeneration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

@Success
public class UserSucess {

    private final UserService userService;

    @Autowired
    public UserSucess(UserService userService) {
        this.userService = userService;
    }

    public void userSuccess(UserDto userDto){
        UserDto user = userService.findByTrackId(userDto.getTrackId());
        if(user != null){
            user.setStatus(Status.SENT_TO_DOTIN);
            userService.sendToDotinTopic(user);
            userService.save(user);
        }
    }
}
