package ru.marchenko.regionsdirectory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
        log.info("Getting all regions on" + new Date());

        return regionsMapper.findAll();
    }

    @Override
    @Cacheable(value = "regions")
    public Region findById(long id) {
        Region region = regionsMapper.findById(id);

        log.info("Getting region: "+ region + " by id: " + id + " on " + new Date());

        return region;
    }

    @Override
    @CacheEvict(value = "regions")
    public Region deleteById(long id) {
        Region region = regionsMapper.findById(id);

        regionsMapper.deleteById(id);

        log.info("Deleting region: " + region + " by id: " + id + " on " + new Date());

        return region;
    }


    @Override
    public Region insert(Region region) {
        log.info("Inserting region: " + region + " on " + new Date());

        if (isExist(region)) {
            log.info("Region: " + region + " is new; it will be inserted");
            regionsMapper.insert(region);
            return region;
        }

        else {
            log.info("This region: " + region + " is not new; it will not be inserted");
            return null;
        }
    }

    @Override
    @CachePut(value = "regions", key = "#region.id")
    public Region update(Region region) {
        log.info("Updating region: " + region + " on " + new Date());

        if (isExist(region)) {
            log.info("Region: " + region + " exist; it will be updated");

            regionsMapper.update(region);

            return region;
        }

        else {
            log.info("This region: " + region + " does not exist; it will not be updated");
            return null;
        }
    }

    private boolean isExist(Region region){
        return regionsMapper.findByNameAndAbbreviatedName(region.getName(), region.getAbbreviatedName()) == null;
    }

}
