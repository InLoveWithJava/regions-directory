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
        //

        Assert.assertEquals(Optional.of(REGION),ofNullable(cacheManager.getCache("regions")).map(c -> c.get(REGION.getId(), Region.class)));
    }

}
