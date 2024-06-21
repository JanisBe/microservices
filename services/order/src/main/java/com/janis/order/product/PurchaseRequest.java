package com.janis.order.product;

import jakarta.validation.constraints.NotNull;

public record PurchaseRequest(
        @NotNull(message = "Product cannot be null")
        Integer productId,
        @NotNull(message = "Quantity cannot be null")
        double quantity
) {
}
