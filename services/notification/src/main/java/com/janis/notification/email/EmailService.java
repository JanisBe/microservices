package com.janis.notification.email;

import com.janis.notification.kafka.order.OrderConfirmation;
import com.janis.notification.kafka.payment.PaymentConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDateTime;
import java.util.Map;

import static com.janis.notification.email.EmailTemplates.ORDER_CONFIRMATION;
import static com.janis.notification.email.EmailTemplates.PAYMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(PaymentConfirmation paymentConfirmation) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessage.setFrom("komornikApp@gmail.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplateName();
        Map<String, Object> variables = Map.of(
            "customerName", paymentConfirmation.customerFirstName(),
            "orderReference", paymentConfirmation.orderReference(),
            "amount", paymentConfirmation.amount(),
            "paymentMethod", paymentConfirmation.paymentMethod().name(),
            "paymentDate", LocalDateTime.now()
        );
        Context context = new Context();
        context.setVariables(variables);
        helper.setTo(paymentConfirmation.customerEmail());
        helper.setSubject(PAYMENT_CONFIRMATION.getSubject());
        try {
            String html = templateEngine.process(templateName, context);
            helper.setText(html, true);
            mailSender.send(mimeMessage);
            log.info("Payment confirmation email successfully sent to {}", paymentConfirmation.customerEmail());
        } catch (MessagingException e) {
            log.warn("Failed to send payment confirmation email to {}", paymentConfirmation.customerEmail(), e);
        }

    }

    @Async
    public void sendOrderSuccessEmail(OrderConfirmation orderConfirmation) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        mimeMessage.setFrom("komornikApp@gmail.com");
        final String templateName = ORDER_CONFIRMATION.getTemplateName();
        Map<String, Object> variables = Map.of(
                "customerName", orderConfirmation.customer().firstName(),
                "orderReference", orderConfirmation.orderReference(),
                "totalAmount", orderConfirmation.totalAmount(),
                "paymentMethod", orderConfirmation.paymentMethod().name(),
                "paymentDate", LocalDateTime.now(),
                "products", orderConfirmation.purchaseResponses()
        );
        Context context = new Context();
        context.setVariables(variables);
        helper.setTo(orderConfirmation.customer().email());
        helper.setSubject(ORDER_CONFIRMATION.getSubject());
        try {
            String html = templateEngine.process(templateName, context);
            helper.setText(html, true);
            mailSender.send(mimeMessage);
            log.info("Payment confirmation email successfully sent to {}", orderConfirmation.customer().email());
        } catch (MessagingException e) {
            log.warn("Failed to send payment confirmation email to {}", orderConfirmation.customer().email(), e);
        }

    }

}
