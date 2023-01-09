package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.MasterRequestDto;
import com.example.autoservice.dto.MasterResponseDto;
import com.example.autoservice.model.Master;
import org.springframework.stereotype.Component;

@Component
public class MasterMapper implements Mapper<Master, MasterRequestDto, MasterResponseDto> {
    @Override
    public Master toModel(MasterRequestDto requestDto) {
        Master master = new Master();
        master.setFullName(requestDto.getFullName());
        return master;
    }

    @Override
    public MasterResponseDto toResponseDto(Master master) {
        MasterResponseDto responseDto = new MasterResponseDto();
        responseDto.setId(master.getId());
        responseDto.setFullName(master.getFullName());
        return responseDto;
    }
}
