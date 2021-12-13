package lev.philippov.mssc_beer_service.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lev.philippov.mssc_beer_service.web.models.BeerDto;
import lev.philippov.mssc_beer_service.web.models.BeerStyleEnum;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    private final static String API_V1_URI = "/api/v1/beer";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get(API_V1_URI+"/"+ UUID.randomUUID()))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createNewBeer() throws Exception {
        String jsonBeerDto = mapper.writeValueAsString(getValidBeerDto());
        mockMvc.perform(post(API_V1_URI).contentType(MediaType.APPLICATION_JSON).content(jsonBeerDto)).andExpect(status().isNoContent());
    }

    @Test
    void updateBeerById() throws Exception {
        String jsonBeerDto = mapper.writeValueAsString(getValidBeerDto());
        mockMvc.perform(put(API_V1_URI + "/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON).content(jsonBeerDto))
                .andExpect(status().isNoContent());
    }

    private BeerDto getValidBeerDto(){
        return BeerDto.builder().beerName("My Beer").beerStyle(BeerStyleEnum.IPA).price(new BigDecimal("12")).upc(123123123123L).build();
    }
}