package ru.marchenko.regionsdirectory.service;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.marchenko.regionsdirectory.model.Region;

/**
 * @author Created by Vladislav Marchenko on 30.09.2020
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RegionsServiceTest {

    private final static String REGION_NAME = "region";

    private final static String REGION_ABBREVIATED_NAME = "reg";

    private final static String REGION_UPDATED_NAME = "upd region";

    private final Region region = new Region(REGION_NAME, REGION_ABBREVIATED_NAME);

    @Autowired
    private RegionsService regionsService;

    @AfterEach
    void cleanUp() {
        regionsService
                .findAll()
                .forEach(it -> regionsService.deleteById(it.getId()));
    }

    @Test
    public void testInsertMethod() {
        regionsService.insert(region);
        Assert.assertNotEquals((Long) 0L,  region.getId());
        Assert.assertEquals(REGION_ABBREVIATED_NAME, regionsService.findById(region.getId()).getAbbreviatedName());
        Assert.assertEquals(REGION_NAME, regionsService.findById(region.getId()).getName());
    }

    @Test
    public void testFindMethods() {
        regionsService.insert(region);

        Assert.assertNotNull(regionsService.findById(region.getId()));
        Assert.assertNotEquals(0, regionsService.findAll().size());
        Assert.assertEquals(REGION_NAME, regionsService.findById(region.getId()).getName());
        Assert.assertEquals(REGION_ABBREVIATED_NAME, regionsService.findById(region.getId()).getAbbreviatedName());
    }

    @Test
    public void testUpdateMethod() {
        regionsService.insert(region);

        Long id = region.getId();

        region.setName(REGION_UPDATED_NAME);

        Assert.assertTrue(regionsService.update(region));
        Assert.assertEquals(id, region.getId());
        Assert.assertEquals(REGION_ABBREVIATED_NAME, regionsService.findById(region.getId()).getAbbreviatedName());
        Assert.assertNotEquals(REGION_NAME, regionsService.findById(region.getId()).getName());
    }

    @Test
    public void testDeleteMethods() {
        regionsService.insert(region);
        System.out.println(regionsService.findAll());
        Assert.assertTrue(regionsService.findAll().contains(region));

        regionsService.deleteById(region.getId());

        Assert.assertFalse(regionsService.findAll().contains(region));
    }

}