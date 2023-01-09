package com.example.autoservice.controller;

import com.example.autoservice.dto.WareRequestDto;
import com.example.autoservice.dto.WareResponseDto;
import com.example.autoservice.dto.mapper.WareMapper;
import com.example.autoservice.service.WareService;
import com.example.autoservice.model.Ware;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wares")
public class WareController {
    private final WareService wareService;
    private final WareMapper wareMapper;

    public WareController(WareService wareService, WareMapper wareMapper) {
        this.wareService = wareService;
        this.wareMapper = wareMapper;
    }

    @PostMapping
    @Operation(summary = "Create new ware with name and price")
    public WareResponseDto create(@RequestBody WareRequestDto requestDto) {
        Ware ware = wareMapper.toModel(requestDto);
        wareService.save(ware);
        return wareMapper.toResponseDto(ware);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing ware by id, with new name and price")
    public WareResponseDto update(@PathVariable Long id,
                                  @RequestBody WareRequestDto requestDto) {
        Ware ware = wareMapper.toModel(requestDto);
        ware.setId(id);
        wareService.save(ware);
        return wareMapper.toResponseDto(ware);
    }
}
