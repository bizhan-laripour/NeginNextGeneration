package net.neginNextGeneration.mapper;


import net.neginNextGeneration.dto.UserDto;
import net.neginNextGeneration.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserDto entityToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setLastName(user.getLastName());
        userDto.setName(user.getName());
        userDto.setTrackId(user.getTrackingId());
        userDto.setStatus(user.getStatus());
        userDto.setId(user.getId());
        return userDto;
    }


    public User dtoToEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setLastName(userDto.getLastName());
        user.setStatus(userDto.getStatus());
        user.setName(userDto.getName());
        user.setTrackingId(userDto.getTrackId());
        return user;
    }

    public List<UserDto> entitiesToDStos(List<User> kafkaEntities){
        List<UserDto> userDtos = new ArrayList<>();
        kafkaEntities.forEach(a -> userDtos.add(entityToDto(a)));
        return userDtos;
    }

    public List<User> dtosToEntities(List<UserDto> userDtos){
        List<User> kafkaEntities = new ArrayList<>();
        userDtos.forEach(a -> kafkaEntities.add(dtoToEntity(a)));
        return kafkaEntities;
    }
}
