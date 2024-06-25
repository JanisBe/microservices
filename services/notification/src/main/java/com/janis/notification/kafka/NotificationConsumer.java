package com.janis.notification.kafka;

import com.janis.notification.email.EmailService;
import com.janis.notification.kafka.order.OrderConfirmation;
import com.janis.notification.kafka.payment.PaymentConfirmation;
import com.janis.notification.notification.Notification;
import com.janis.notification.notification.NotificationRepository;
import com.janis.notification.notification.NotificationType;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming message from payment-topic {}", paymentConfirmation);
        repository.save(Notification.builder()
                .type(NotificationType.PAYMENT_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build());
        emailService.sendPaymentSuccessEmail(paymentConfirmation);
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming message from order-topic {}", orderConfirmation);
        repository.save(Notification.builder()
                .type(NotificationType.ORDER_CONFIRMATION)
                .notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build());
        emailService.sendOrderSuccessEmail(orderConfirmation);
    }

}
