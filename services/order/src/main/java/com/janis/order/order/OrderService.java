package com.janis.order.order;

import com.janis.order.client.CustomerClient;
import com.janis.order.client.CustomerResponse;
import com.janis.order.exception.BusinessException;
import com.janis.order.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderMapper mapper;

    public Integer createOrder(OrderRequest request) {
        CustomerResponse customer = customerClient.findById(request.customerId()).orElseThrow(() -> new BusinessException("Customer not found"));
        productClient.purchaseProducts(request.products());
        Order order = orderRepository.save(mapper.toOrder(request));


        return order.getId(); //tu skończyłem
    }
}
