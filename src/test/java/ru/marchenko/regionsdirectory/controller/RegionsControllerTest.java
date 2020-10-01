package ru.marchenko.regionsdirectory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.marchenko.regionsdirectory.model.Region;
import ru.marchenko.regionsdirectory.service.RegionsService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Created by Vladislav Marchenko on 01.10.2020
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RegionsController.class)
class RegionsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RegionsService regionsService;

    private final List<Region> REGIONS = Arrays.asList(
            new Region("region1", "reg1"),
            new Region("region2", "reg2"),
            new Region("region3", "reg3")
    );

    private final Region REGION = new Region(1L,"region", "reg");

    private final Region NOT_INSERTED_REGION = new Region(0L,"region", "reg");

    @Test
    void testGetAllAPIWhenStatusIsOK() throws Exception {

        doReturn(REGIONS).when(regionsService).findAll();

        mvc.perform(MockMvcRequestBuilders
                .get("/regions/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(REGIONS.get(0).getName())));
    }

    @Test
    void testGetAllAPIWhenStatusIsNotFound() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/regions/all")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetByIdAPIWhenStatusIsOK() throws Exception {

        doReturn(REGIONS.get(0)).when(regionsService).findById(1);

        mvc.perform(MockMvcRequestBuilders
                .get("/regions/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(REGIONS.get(0).getName())));
    }

    @Test
    void testGetByIdAPIWhenStatusIsNotFound() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .get("/regions/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveAPIWhenStatusIsCreated() throws Exception {
        doReturn(REGION).when(regionsService).insert(any());
        doReturn(REGION).when(regionsService).findById(1);

        mvc.perform( MockMvcRequestBuilders
                .post("/regions/save")
                .content(new ObjectMapper().writeValueAsString(new Region("region", "reg")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(REGION.getName())));
    }

    @Test
    void testSaveAPIWhenStatusIsNotImplemented() throws Exception {
        doReturn(NOT_INSERTED_REGION).when(regionsService).insert(any());
        doReturn(NOT_INSERTED_REGION).when(regionsService).findById(0);

        mvc.perform( MockMvcRequestBuilders
                .post("/regions/save")
                .content(new ObjectMapper().writeValueAsString(new Region("region", "reg")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotImplemented());
    }

    @Test
    void testDeleteByIdAPIWhenStatusIsOK() throws Exception {
        doReturn(REGIONS.get(0)).when(regionsService).deleteById(1);

        mvc.perform(MockMvcRequestBuilders
                .delete("/regions/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(REGIONS.get(0).getName())));
    }

    @Test
    void testDeleteByIdAPIWhenStatusIsNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/regions/1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAPIWhenStatusIsOK() throws Exception {
        doReturn(true).when(regionsService).update(any());

        mvc.perform( MockMvcRequestBuilders
                .put("/regions/update")
                .content(new ObjectMapper().writeValueAsString(new Region("region", "reg")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(REGION.getName())));
    }

    @Test
    void testUpdateAPIWhenStatusIsNotModified() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                .put("/regions/update")
                .content(new ObjectMapper().writeValueAsString(new Region("region", "reg")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());
    }
}