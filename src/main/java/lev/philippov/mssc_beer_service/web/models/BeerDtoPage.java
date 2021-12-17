package lev.philippov.mssc_beer_service.web.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class BeerDtoPage extends PageImpl<BeerDto> implements Serializable {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerDtoPage(@JsonProperty("totalPages") int totalPages,
                       @JsonProperty("totalElements") long totalElements,
                       @JsonProperty("last") boolean last,
                       @JsonProperty("number") int number,
                       @JsonProperty("size") int size,
                       @JsonProperty("numberOfElements") int numberOfElements,
                       @JsonProperty("first") boolean first,
                       @JsonProperty("content") List<BeerDto> content,
                       @JsonProperty("sort") JsonNode sort,
                       @JsonProperty("pageable") JsonNode pageable){
        super(content, PageRequest.of(number,size),totalElements);
    }


    public BeerDtoPage(List<BeerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerDtoPage(List<BeerDto> content) {
        super(content);
    }
}
