package com.janis.order.orderLine;

import com.janis.order.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        if (orderLineRequest == null) {
            return null;
        }
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .order(Order.builder().id(orderLineRequest.orderId()).build())
                .productId(orderLineRequest.productId())
                .quantity(orderLineRequest.quantity())
                .build();
    }

    public OrderLineResponse fromOrderLine(OrderLine orderLine) {
        if (orderLine == null) {
            return null;
        }
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
