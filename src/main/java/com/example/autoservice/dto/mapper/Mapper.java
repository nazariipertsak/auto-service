package com.example.autoservice.dto.mapper;

public interface Mapper<O, Q, S> {
    O toModel(Q requestDto);

    S toResponseDto(O object);
}
