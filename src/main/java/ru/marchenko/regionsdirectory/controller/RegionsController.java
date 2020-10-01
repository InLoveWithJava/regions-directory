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

    @GetMapping(value = "/all")
    public ResponseEntity<List<Region>> getAll() {
        List<Region> regions = regionsService.findAll();

        return regions != null &&  !regions.isEmpty()
                ? new ResponseEntity<>(regions, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Region> getById(@PathVariable Long id) {
        Region region = regionsService.findById(id);

        return region != null
                ? new ResponseEntity<>(region, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Region> save(@RequestBody Region region) {
        region = regionsService.insert(region);

        return region == null
                ? new ResponseEntity<>(HttpStatus.FORBIDDEN)
                : (region.getId().compareTo(0L) > 0 && regionsService.findById(region.getId()) != null)
                    ? new ResponseEntity<>(region, HttpStatus.CREATED)
                    : new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Region> deleteById(@PathVariable Long id) {
        Region region = regionsService.deleteById(id);

        return region != null
                ? new ResponseEntity<>(region, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Region> update(@RequestBody Region region) {
        Region region1 = regionsService.update(region);

        return region1 != null
                ? new ResponseEntity<>(region, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
