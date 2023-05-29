package net.neginNextGeneration.events.success;

import net.neginNextGeneration.annotations.Success;
import net.neginNextGeneration.dto.DotinDto;
import net.neginNextGeneration.dto.UserDto;
import net.neginNextGeneration.enums.Status;
import net.neginNextGeneration.service.DotinService;
import net.neginNextGeneration.service.UserService;

@Success
public class DotinSucess {

    private final DotinService dotinService;
    private final UserService userService;

    public DotinSucess(DotinService dotinService, UserService userService) {
        this.dotinService = dotinService;
        this.userService = userService;
    }

    public void dotinSuccess(DotinDto dotinDto) {
        UserDto userDto = userService.findByTrackId(dotinDto.getTrackId());
        userDto.setStatus(Status.SUCCESS);
        userService.save(userDto);
        dotinService.save(dotinDto);
    }
}
