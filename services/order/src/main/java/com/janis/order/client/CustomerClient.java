package com.janis.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
    name = "customer-service",
    url = "${application.config.customer-url}"
)
public interface CustomerClient {
    @GetMapping("/customers/find/{customerId}")
    Optional<CustomerResponse> findById(@PathVariable("customerId")String customerId);
}
