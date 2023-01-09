package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.WareRequestDto;
import com.example.autoservice.dto.WareResponseDto;
import com.example.autoservice.model.Ware;
import org.springframework.stereotype.Component;

@Component
public class WareMapper implements Mapper<Ware, WareRequestDto, WareResponseDto> {
    @Override
    public Ware toModel(WareRequestDto requestDto) {
        Ware ware = new Ware();
        ware.setName(requestDto.getName());
        ware.setPrice(requestDto.getPrice());
        return ware;
    }

    @Override
    public WareResponseDto toResponseDto(Ware ware) {
        WareResponseDto responseDto = new WareResponseDto();
        responseDto.setId(ware.getId());
        responseDto.setName(ware.getName());
        responseDto.setPrice(ware.getPrice());
        return responseDto;
    }
}
