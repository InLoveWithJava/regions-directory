package ru.marchenko.regionsdirectory.mapper;

import org.apache.ibatis.annotations.*;
import ru.marchenko.regionsdirectory.model.Region;

import java.util.List;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@Mapper
public interface RegionsMapper {

    @Select("select * from regions")
    List<Region> findAll();

    @Select("select * from regions where id = #{id}")
    Region findById(long id);

    @Select("select * from regions where name = #{name} and abbreviated_name = #{abbreviatedName}")
    Region findByNameAndAbbreviatedName(String name, String abbreviatedName);

    @Delete("delete from regions where id = #{id}")
    void deleteById(long id);

    @Insert("insert into regions(name, abbreviated_name) " +
            " values (#{name}, #{abbreviatedName})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insert(Region region);

    @Update("update regions set name=#{name}, " +
            " abbreviated_name=#{abbreviatedName} where id=#{id}")
    void update(Region region);

}
