package ru.marchenko.regionsdirectory.service;

import ru.marchenko.regionsdirectory.model.Region;

import java.util.List;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
public interface RegionsService {
    List<Region> findAll();

    Region findById(long id);

    int deleteById(long id);

    int insert(Region region);

    int update(Region region);

    int deleteAll();
}
