package com.example.autoservice.controller;

import com.example.autoservice.dto.FavorRequestDto;
import com.example.autoservice.dto.FavorResponseDto;
import com.example.autoservice.dto.StatusDto;
import com.example.autoservice.dto.mapper.FavorMapper;
import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Master;
import com.example.autoservice.service.FavorService;
import com.example.autoservice.service.MasterService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/favors")
public class FavorController {
    private final FavorService favorService;
    private final FavorMapper favorMapper;
    private final MasterService masterService;

    public FavorController(FavorService favorService, FavorMapper favorMapper,
                           MasterService masterService) {
        this.favorService = favorService;
        this.favorMapper = favorMapper;
        this.masterService = masterService;
    }

    @PostMapping
    @Operation(summary = "Create new favor with name, price and master id")
    public FavorResponseDto create(@RequestBody FavorRequestDto requestDto) {
        Favor favor = favorMapper.toModel(requestDto);
        favor.setStatus(Favor.FavorStatus.NOT_PAID);
        Master master = masterService.getById(requestDto.getMasterId());
        favor = favorService.save(favor);
        master.getFavors().add(favor);
        masterService.save(master);
        return favorMapper.toResponseDto(favor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update favor by id, with new name, price and master id")
    public FavorResponseDto update(@PathVariable Long id,
                                   @RequestBody FavorRequestDto requestDto) {
        Favor favor = favorMapper.toModel(requestDto);
        favor.setId(id);
        favor = favorService.save(favor);
        return favorMapper.toResponseDto(favor);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update status of favor by id, can be paid or unpaid")
    public FavorResponseDto updateStatus(@PathVariable Long id,
                                         @RequestBody StatusDto status) {
        Favor favor = favorService.getById(id);
        return favorMapper.toResponseDto(favorService.updateStatus(favor, status.getStatus()));
    }
}
