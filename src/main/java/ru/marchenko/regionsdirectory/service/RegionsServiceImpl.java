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
    public int deleteById(long id) {
        return regionsMapper.deleteById(id);
    }

    @Override
    public int insert(Region region) {
        return regionsMapper.insert(region);
    }

    @Override
    public int update(Region region) {
        return regionsMapper.update(region);
    }

    @Override
    public int deleteAll() {
        return regionsMapper.deleteAll();
    }
}
