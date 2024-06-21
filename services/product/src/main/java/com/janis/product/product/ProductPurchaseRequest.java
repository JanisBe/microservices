package com.janis.product.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product cannot be null")
        Integer productId,
        @NotNull(message = "Quantity cannot be null")
        double quantity
) {
}
