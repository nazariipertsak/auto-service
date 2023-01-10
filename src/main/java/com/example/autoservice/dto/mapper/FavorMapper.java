package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.FavorRequestDto;
import com.example.autoservice.dto.FavorResponseDto;
import com.example.autoservice.model.Favor;
import org.springframework.stereotype.Component;

@Component
public class FavorMapper implements Mapper<Favor, FavorRequestDto, FavorResponseDto> {
    @Override
    public Favor toModel(FavorRequestDto requestDto) {
        Favor favor = new Favor();
        favor.setPrice(requestDto.getPrice());
        favor.setName(requestDto.getName());
        favor.setMasterId(requestDto.getMasterId());
        return favor;
    }

    @Override
    public FavorResponseDto toResponseDto(Favor favor) {
        FavorResponseDto favorResponseDto = new FavorResponseDto();
        favorResponseDto.setId(favor.getId());
        favorResponseDto.setName(favor.getName());
        favorResponseDto.setPrice(favor.getPrice());
        favorResponseDto.setMasterId(favor.getMasterId());
        favorResponseDto.setStatus(favor.getStatus());
        return favorResponseDto;
    }
}
