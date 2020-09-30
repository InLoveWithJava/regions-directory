package ru.marchenko.regionsdirectory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.marchenko.regionsdirectory.mapper.RegionsMapper;
import ru.marchenko.regionsdirectory.model.Region;

import java.util.List;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionsController {
    private final RegionsMapper regionsMapper;

    @GetMapping("/all")
    public List<Region> getAll() {
        return regionsMapper.findAll();
    }
}
