package com.janis.notification.kafka.order;

import java.math.BigDecimal;

public record Product(
        Integer productId,
        String productName,
        String productDescription,
        BigDecimal price,
        double quantity
) {
}
