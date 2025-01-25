package com.FreelancersBackend.api.payPal;

import com.FreelancersBackend.api.posts.PostsService;
import com.FreelancersBackend.models.PaymentDto;
import com.FreelancersBackend.service.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    private PayPalService payPalService;
    private PayPalService paymentService;
    private PostsService postsService;

    /*@PostMapping("/pay")
    public String createPayment() {
        try {
            Payment payment = payPalService.createPayment(
                    20.0, // Kwota
                    "PLN", // Waluta
                    "Priority Post Payment",
                    "http://localhost:3000/cancel",
                    "http://localhost:3000/success"
            );
            return payment.getLinks().stream()
                    .filter(link -> link.getRel().equals("approval_url"))
                    .findFirst()
                    .map(Links::getHref)
                    .orElseThrow(() -> new RuntimeException("Approval URL not found"));
        } catch (PayPalRESTException e) {
            throw new RuntimeException("Error occurred while creating payment", e);
        }
    }

    @PostMapping("/execute")
    public Payment executePayment(@RequestParam String paymentId, @RequestParam String payerId) {
        try {
            return payPalService.executePayment(paymentId, payerId);
        } catch (PayPalRESTException e) {
            throw new RuntimeException("Error occurred while executing payment", e);
        }
    }

    @PostMapping("/api/posts/{postId}/mark-priority")
    public ResponseEntity<Void> markPostAsPriority(@PathVariable Long postId, @RequestBody PaymentDto paymentDto) {
        // Weryfikacja płatności po ID (opcjonalne, zależy od twojej logiki)
        paymentService.verifyPayment(paymentDto.getPaymentId());

        // Oznacz post jako priorytetowy
        postsService.markAsPriority(Math.toIntExact(postId));
        return ResponseEntity.ok().build();
    }*/
}

