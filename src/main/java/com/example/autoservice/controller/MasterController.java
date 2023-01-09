package com.example.autoservice.controller;

import com.example.autoservice.dto.FavorResponseDto;
import com.example.autoservice.dto.MasterRequestDto;
import com.example.autoservice.dto.MasterResponseDto;
import com.example.autoservice.dto.mapper.FavorMapper;
import com.example.autoservice.dto.mapper.MasterMapper;
import com.example.autoservice.model.Master;
import com.example.autoservice.service.MasterService;
import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/masters")
public class MasterController {
    private final FavorMapper favorMapper;
    private final MasterService masterService;
    private final MasterMapper masterMapper;

    public MasterController(FavorMapper favorMapper, MasterService masterService, MasterMapper masterMapper) {
        this.favorMapper = favorMapper;
        this.masterService = masterService;
        this.masterMapper = masterMapper;
    }

    @PostMapping
    @Operation(summary = "Create new master with full name")
    public MasterResponseDto create(@RequestBody MasterRequestDto requestDto) {
        Master master = masterMapper.toModel(requestDto);
        masterService.save(master);
        return masterMapper.toResponseDto(master);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update master by id, with new full name")
    public MasterResponseDto update(@PathVariable Long id,
                                    @RequestBody MasterRequestDto requestDto) {
        Master master = masterMapper.toModel(requestDto);
        master.setId(id);
        masterService.save(master);
        return masterMapper.toResponseDto(master);
    }

    @GetMapping("/{id}/favors")
    @Operation(summary = "Get list of favors of master by id")
    public List<FavorResponseDto> getMasterFavors(@PathVariable Long id) {
        return masterService.getById(id).getFavors().stream()
                .map(favorMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}/salary")
    @Operation(summary = "Count master's salary and give it to him. Changes favor's status to paid")
    public BigDecimal getMasterSalary(@PathVariable Long id) {
        Master master = masterService.getById(id);
        return masterService.countSalary(master.getFavors());
    }
}
