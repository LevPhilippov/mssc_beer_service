package lev.philippov.mssc_beer_service.services.order.listeners;

import guru.sfg.brewery.model.events.ValidateOrderResponse;
import guru.sfg.brewery.model.events.ValidateOrderRequest;
import lev.philippov.mssc_beer_service.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {

    private final BeerOrderValidator validator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_REQUEST_QUEUE)
    public void listenAndValidate(@Payload ValidateOrderRequest validateOrderRequest){
        Boolean isValid = validator.validate(validateOrderRequest.getBeerOrderDto());
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,ValidateOrderResponse.builder()
                .beerOrderId(validateOrderRequest.getBeerOrderDto().getId())
                .isValid(isValid)
                .build());
    }
}
