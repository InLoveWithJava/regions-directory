package ru.marchenko.regionsdirectory.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import ru.marchenko.regionsdirectory.model.Region;

import java.util.List;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@Mapper
public interface RegionsMapper {

    @Select("select * from regions")
    List<Region> findAll();
}
