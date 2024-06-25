package com.janis.order.kafka;

import com.janis.order.client.CustomerResponse;
import com.janis.order.order.PaymentMethod;
import com.janis.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
    String orderReference,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    CustomerResponse customer,
    List<PurchaseResponse> products
) {
}
