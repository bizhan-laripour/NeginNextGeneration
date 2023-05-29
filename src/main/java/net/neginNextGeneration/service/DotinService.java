package net.neginNextGeneration.service;


import net.neginNextGeneration.dto.DotinDto;
import net.neginNextGeneration.mapper.DotinMapper;
import net.neginNextGeneration.repository.DotinRepository;
import org.springframework.stereotype.Service;

@Service
public class DotinService {


    private final DotinRepository dotinRepository;

    private final DotinMapper dotinMapper;

    public DotinService(DotinRepository dotinRepository, DotinMapper dotinMapper) {
        this.dotinRepository = dotinRepository;
        this.dotinMapper = dotinMapper;
    }

    public void save(DotinDto dotinDto){
        dotinRepository.save(dotinMapper.dtoToEntity(dotinDto));
    }

    public DotinDto findByTrackId(String trackId){
        if(dotinRepository.findByTrackingId(trackId).isPresent())
            return dotinMapper.entityToDto(dotinRepository.findByTrackingId(trackId).get());
        else
            return null;
    }





}
