package com.janis.order.client;

public record CustomerResponse(
    String id,
    String firstName,
    String lastName,
    String email
) {
}
