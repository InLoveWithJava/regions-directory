package ru.marchenko.regionsdirectory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.marchenko.regionsdirectory.mapper.RegionsMapper;
import ru.marchenko.regionsdirectory.model.Region;

import java.util.List;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@Service
@RequiredArgsConstructor
public class RegionsServiceImpl implements RegionsService {

    private final RegionsMapper regionsMapper;

    @Override
    public List<Region> findAll() {
        return regionsMapper.findAll();
    }

    @Override
    public Region findById(long id) {
        return regionsMapper.findById(id);
    }

    @Override
    public Region deleteById(long id) {
        Region region = regionsMapper.findById(id);

        regionsMapper.deleteById(id);

        return region;
    }

    @Override
    public Region insert(Region region) {
        regionsMapper.insert(region);

        return region;
    }

    @Override
    public boolean update(Region region) {

        return regionsMapper.update(region) > 0;
    }

}
