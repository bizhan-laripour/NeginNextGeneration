package net.neginNextGeneration.mapper;

import net.neginNextGeneration.dto.DotinDto;
import net.neginNextGeneration.dto.UserDto;
import net.neginNextGeneration.entity.Dotin;
import org.springframework.stereotype.Component;

@Component
public class DotinMapper {

    public DotinDto entityToDto(Dotin dotin){
        DotinDto dotinDto = new DotinDto();
        dotinDto.setId(dotin.getId());
        dotinDto.setLastName(dotin.getLastName());
        dotinDto.setStatus(dotin.getStatus());
        dotinDto.setTrackId(dotin.getTrackingId());
        return dotinDto;
    }

    public Dotin dtoToEntity(DotinDto dotinDto){
        Dotin dotin = new Dotin();
        dotin.setId(dotinDto.getId());
        dotin.setStatus(dotinDto.getStatus());
        dotin.setName(dotinDto.getName());
        dotin.setLastName(dotinDto.getLastName());
        return dotin;
    }

    public Dotin UserToDotin(UserDto userDto){
        Dotin dotin = new Dotin();
        dotin.setLastName(userDto.getLastName());
        dotin.setName(userDto.getName());
        dotin.setStatus(userDto.getStatus());
        dotin.setTrackingId(userDto.getTrackId());
        return dotin;
    }
}
