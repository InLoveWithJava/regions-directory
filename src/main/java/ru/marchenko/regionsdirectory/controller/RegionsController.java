package ru.marchenko.regionsdirectory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.marchenko.regionsdirectory.model.Region;
import ru.marchenko.regionsdirectory.service.RegionsService;

import java.util.List;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionsController {
    private final RegionsService regionsService;

    @GetMapping("/all")
    public ResponseEntity<List<Region>> getAll() {
        List<Region> regions = regionsService.findAll();
        return regions != null &&  !regions.isEmpty()
                ? new ResponseEntity<>(regions, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/save")
    public ResponseEntity<Region> save(@RequestBody Region region) {
        regionsService.insert(region);

        Long id = region.getId();

        return id.compareTo(0L) > 0
                ? new ResponseEntity<>(region, HttpStatus.CREATED)
                : new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
