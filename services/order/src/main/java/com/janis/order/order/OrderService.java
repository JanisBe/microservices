package com.janis.order.order;

import com.janis.order.client.CustomerClient;
import com.janis.order.client.CustomerResponse;
import com.janis.order.exception.BusinessException;
import com.janis.order.kafka.OrderConfirmation;
import com.janis.order.kafka.OrderProducer;
import com.janis.order.orderLine.OrderLineRequest;
import com.janis.order.orderLine.OrderLineService;
import com.janis.order.payment.PaymentClient;
import com.janis.order.payment.PaymentRequest;
import com.janis.order.product.ProductClient;
import com.janis.order.product.PurchaseRequest;
import com.janis.order.product.PurchaseResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        CustomerResponse customer = customerClient.findById(request.customerId())
                .orElseThrow(() -> new BusinessException("Customer not found"));
        List<PurchaseResponse> purchaseResponses = productClient.purchaseProducts(request.products());
        Order order = orderRepository.save(mapper.toOrder(request));
        for (PurchaseRequest product : request.products()) {
            orderLineService.saveOrderLine(new OrderLineRequest(null, order.getId(), product.productId(), product.quantity()));
        }

        paymentClient.requestOrderPayment(new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                request.reference(),
                customer
        ));

        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchaseResponses));

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository
                .findAll()
                .stream()
                .map(mapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer id) {
        Order order = orderRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return mapper.fromOrder(order);
    }
}
