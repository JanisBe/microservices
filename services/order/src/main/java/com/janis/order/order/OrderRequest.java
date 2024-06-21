package com.janis.order.order;

import com.janis.order.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method cannot be null")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer cannot be null")
        @NotEmpty(message = "Customer cannot be empty")
        @NotBlank(message = "Customer cannot be blank")
        String customerId,
        @NotEmpty(message = "Products cannot be empty")
        List<PurchaseRequest> products) {
}
