package net.neginNextGeneration.events.fail;

import net.neginNextGeneration.annotations.Failure;
import net.neginNextGeneration.dto.DotinDto;
import net.neginNextGeneration.dto.UserDto;
import net.neginNextGeneration.enums.Status;
import net.neginNextGeneration.service.DotinService;
import net.neginNextGeneration.service.UserService;

@Failure
public class DotinFaiure {

    private final DotinService dotinService;

    public DotinFaiure(DotinService dotinService){
        this.dotinService = dotinService;
    }


    public void dotinFailureProccess(DotinDto dotinDto){
        DotinDto dotin = dotinService.findByTrackId(dotinDto.getTrackId());
        if(dotin != null){
            dotin.setStatus(Status.FAIL);
        }
    }
}
