package ru.marchenko.regionsdirectory.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;
import ru.marchenko.regionsdirectory.model.Region;
import ru.marchenko.regionsdirectory.util.SortOrder;
import ru.marchenko.regionsdirectory.util.SortSetting;
import ru.marchenko.regionsdirectory.util.SortedField;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RegionsServiceTest {

    private static final  String REGION_NAME = "region";

    private static final  String REGION_ABBREVIATED_NAME = "reg";

    private static final  String REGION_UPDATED_NAME = "upd region";

    private static final Region REGION = new Region(REGION_NAME, REGION_ABBREVIATED_NAME);

    private static final Region FIRST_REGION = new Region("a_reg", "c_r");

    private static final Region SECOND_REGION = new Region("b_reg", "b_r");

    private static final Region THIRD_REGION = new Region("c_reg", "a_3");

    private static final List<Region> FIRST_LIST = Arrays.asList(
            FIRST_REGION, SECOND_REGION, THIRD_REGION
    );

    private static final List<Region> SECOND_LIST = Arrays.asList(
            THIRD_REGION, SECOND_REGION, FIRST_REGION
    );

    private static final SortSetting SORT_SETTING_NAME_ASC = new SortSetting(SortedField.NAME, SortOrder.ASC);

    private static final SortSetting SORT_SETTING_NAME_DESC = new SortSetting(SortedField.NAME, SortOrder.DESC);

    private static final SortSetting SORT_SETTING_ABBREVIATED_NAME_ASC = new SortSetting(SortedField.ABBREVIATED_NAME, SortOrder.ASC);

    private static final SortSetting SORT_SETTING_ABBREVIATED_NAME_DESC = new SortSetting(SortedField.ABBREVIATED_NAME, SortOrder.DESC);

    @Autowired
    private RegionsService regionsService;

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    void cleanUp() {
        regionsService
                .findAll()
                .forEach(it -> regionsService.deleteById(it.getId()));
    }

    @Test
    public void testInsertMethod() {
        regionsService.insert(REGION);
        Assert.assertNotEquals((Long) 0L,  REGION.getId());
        Assert.assertEquals(REGION_ABBREVIATED_NAME, regionsService.findById(REGION.getId()).getAbbreviatedName());
        Assert.assertEquals(REGION_NAME, regionsService.findById(REGION.getId()).getName());
        Assert.assertNull(regionsService.insert(REGION));
    }

    @Test
    public void testFindMethods() {
        regionsService.insert(REGION);

        Assert.assertNotNull(regionsService.findById(REGION.getId()));
        Assert.assertNotEquals(0, regionsService.findAll().size());
        Assert.assertEquals(REGION_NAME, regionsService.findById(REGION.getId()).getName());
        Assert.assertEquals(REGION_ABBREVIATED_NAME, regionsService.findById(REGION.getId()).getAbbreviatedName());
    }

    @Test
    public void testUpdateMethod() {
        regionsService.insert(REGION);

        REGION.setName(REGION_UPDATED_NAME);

        Region updRegion = regionsService.update(REGION);

        Assert.assertEquals(REGION.getId(), updRegion.getId());
        Assert.assertEquals(REGION.getAbbreviatedName(), updRegion.getAbbreviatedName());
        Assert.assertNotEquals(REGION_NAME, updRegion.getName());

        REGION.setName(REGION_NAME);
    }

    @Test
    public void testDeleteMethods() {
        regionsService.insert(REGION);
        System.out.println(regionsService.findAll());
        Assert.assertTrue(regionsService.findAll().contains(REGION));

        regionsService.deleteById(REGION.getId());

        Assert.assertFalse(regionsService.findAll().contains(REGION));
    }

    @Test
    public void testCache() {
        Assert.assertEquals(Optional.empty(), ofNullable(cacheManager.getCache("regions")).map(c -> c.get(REGION.getId(), Region.class)));

        regionsService.insert(REGION);
        regionsService.findById(REGION.getId());
        Assert.assertEquals(Optional.of(REGION),ofNullable(cacheManager.getCache("regions")).map(c -> c.get(REGION.getId(), Region.class)));

        regionsService.deleteById(REGION.getId());
        Assert.assertEquals(Optional.empty(), ofNullable(cacheManager.getCache("regions")).map(c -> c.get(REGION.getId(), Region.class)));

        regionsService.insert(REGION);
        regionsService.findById(REGION.getId());
        REGION.setName("new name");
        regionsService.update(REGION);

        Assert.assertEquals(Optional.of(REGION),ofNullable(cacheManager.getCache("regions")).map(c -> c.get(REGION.getId(), Region.class)));
    }

    @Test
    public void testSortMethod() {
        cleanUp();

        FIRST_LIST.forEach(region -> regionsService.insert(region));

        Assert.assertEquals(FIRST_LIST, regionsService.findAllInSortedOrder(SORT_SETTING_NAME_ASC));
        Assert.assertEquals(FIRST_LIST, regionsService.findAllInSortedOrder(SORT_SETTING_ABBREVIATED_NAME_DESC));
        Assert.assertEquals(SECOND_LIST, regionsService.findAllInSortedOrder(SORT_SETTING_ABBREVIATED_NAME_ASC));
        Assert.assertEquals(SECOND_LIST, regionsService.findAllInSortedOrder(SORT_SETTING_NAME_DESC));
    }

}
