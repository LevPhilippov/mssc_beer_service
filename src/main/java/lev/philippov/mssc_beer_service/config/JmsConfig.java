package lev.philippov.mssc_beer_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfig {

    public static final String BREWERY_BEER_QUEUE = "brewery_beer";
    public static final String NEW_INVENTORY_QUEUE = "new_inventory";
    public static final String VALIDATE_ORDER_REQUEST_QUEUE="validate_order";
    public static final String VALIDATE_ORDER_RESPONSE_QUEUE="validate_order_result";


    @Bean
    MessageConverter messageConverter(ObjectMapper objectMapper){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }
}
