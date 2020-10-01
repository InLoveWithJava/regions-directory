package ru.marchenko.regionsdirectory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.marchenko.regionsdirectory.mapper.RegionsMapper;
import ru.marchenko.regionsdirectory.model.Region;

import java.util.Date;
import java.util.List;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RegionsServiceImpl implements RegionsService {

    private final RegionsMapper regionsMapper;

    @Override
    public List<Region> findAll() {
        log.info("getting all regions on" + new Date());

        return regionsMapper.findAll();
    }

    @Override
    public Region findById(long id) {
        Region region = regionsMapper.findById(id);

        log.info("getting region: "+ region + " on " + new Date());

        return region;
    }

    @Override
    public Region deleteById(long id) {
        Region region = regionsMapper.findById(id);

        regionsMapper.deleteById(id);

        log.info("deleting region: " + region + " on " + new Date());

        return region;
    }

    @Override
    public Region insert(Region region) {
        regionsMapper.insert(region);

        log.info("insert region: " + region + " on " + new Date());

        return region;
    }

    @Override
    public boolean update(Region region) {
        log.info("update region: " + region + " on " + new Date());

        return regionsMapper.update(region) > 0;
    }

}
