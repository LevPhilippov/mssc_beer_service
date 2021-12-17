package lev.philippov.mssc_beer_service.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lev.philippov.mssc_beer_service.domain.Beer;
import lev.philippov.mssc_beer_service.services.BeerService;
import lev.philippov.mssc_beer_service.web.models.BeerDto;
import lev.philippov.mssc_beer_service.web.models.BeerStyleEnum;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    private final static String API_V1_URI = "/api/v1/beer";

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void getBeerById() throws Exception {
        when(beerService.findBeerById(any())).thenReturn(BeerDto.builder().build());
        mockMvc.perform(get(API_V1_URI+"/{beerId}",UUID.randomUUID()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(parameterWithName("beerId").description("UUID of desired beer to get")),
                        responseFields(fieldWithPath("id").description("Id of a Beer"),
                                       fieldWithPath("beerName").description("Name of a Beer, Not Null"),
                                       fieldWithPath("beerStyle").description("Style of a Beer, Not Null"),
                                       fieldWithPath("upc").description("Upc of a Beer, Not Null, positive"),
                                       fieldWithPath("quantityOnHands").description("Quantity on hands, nullable"),
                                       fieldWithPath("price").description("Price of a Beer, Not Null, positive"),
                                       fieldWithPath("version").description("Version"),
                                       fieldWithPath("createdDate").description("Creation timestamp"),
                                       fieldWithPath("lastModifiedDate").description("Modification timestamp")))
                );
    }

    @Test
    void createNewBeer() throws Exception {
        String jsonBeerDto = mapper.writeValueAsString(getValidBeerDto());
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        when(beerService.saveNewBeer(any())).thenReturn(BeerDto.builder().build());
        mockMvc.perform(post(API_V1_URI).contentType(MediaType.APPLICATION_JSON).content(jsonBeerDto))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer-new",
                        requestFields(fieldWithPath("id").ignored(),
                                fields.withPath("beerName").description("Name of a Beer, Not Null"),
                                fields.withPath("beerStyle").description("Style of a Beer, Not Null"),
                                fields.withPath("upc").description("Upc of a Beer, Not Null, positive"),
                                fields.withPath("quantityOnHands").ignored(),
                                fields.withPath("price").description("Price of a Beer, Not Null, positive"),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored()
                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        String jsonBeerDto = mapper.writeValueAsString(getValidBeerDto());
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        when(beerService.updateBeer(any(),any())).thenReturn(BeerDto.builder().build());
        mockMvc.perform(put(API_V1_URI + "/{beerId}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON).content(jsonBeerDto))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer-update",
                        pathParameters(parameterWithName("beerId").description("Id of a Beer that should be updated")),
                        requestFields(fieldWithPath("id").ignored(),
                                fields.withPath("beerName").description("Name of a Beer, Not Null"),
                                fields.withPath("beerStyle").description("Style of a Beer, Not Null"),
                                fields.withPath("upc").description("Upc of a Beer, Not Null, positive"),
                                fields.withPath("quantityOnHands").ignored(),
                                fields.withPath("price").description("Price of a Beer, Not Null, positive"),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdDate").ignored(),
                                fields.withPath("lastModifiedDate").ignored()
                        )));
    }

    private BeerDto getValidBeerDto(){
        return BeerDto.builder().beerName("My Beer").beerStyle(BeerStyleEnum.IPA).price(new BigDecimal("12")).upc("123123123123L").build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}