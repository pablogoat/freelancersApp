package com.FreelancersBackend.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    @Value("${paypal.api.base-url}")
    private String paypalBaseUrl;

    private APIContext apiContext;

    private final WebClient webClient;

    public PayPalService(WebClient.Builder webClientBuilder) {
        this.apiContext = new APIContext(clientId, clientSecret, mode);
        this.webClient = webClientBuilder.build();
    }

    /*public Payment createPayment(Double total, String currency, String description, String cancelUrl, String successUrl) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format("%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }*/

    public boolean verifyPayment(String paymentId) {
        try {
            String accessToken = getAccessToken();

            PaymentDetails paymentDetails = webClient.get()
                    .uri(paypalBaseUrl + "/v2/checkout/orders/" + paymentId)
                    .headers(headers -> headers.setBearerAuth(accessToken))
                    .retrieve()
                    .bodyToMono(PaymentDetails.class)
                    .block();

            return "COMPLETED".equalsIgnoreCase(paymentDetails.getStatus());
        } catch (WebClientResponseException e) {
            System.err.println("Payment verification failed: " + e.getMessage());
            return false;
        }
    }

    private String getAccessToken() {
        try {
            AccessTokenResponse response = webClient.post()
                    .uri(paypalBaseUrl + "/v1/oauth2/token")
                    .headers(headers -> headers.setBasicAuth(clientId, clientSecret))
                    .bodyValue("grant_type=client_credentials")
                    .retrieve()
                    .bodyToMono(AccessTokenResponse.class)
                    .block();

            return response.getAccessToken();
        } catch (WebClientResponseException e) {
            // Logowanie błędów
            System.err.println("Failed to get PayPal access token: " + e.getMessage());
            throw new IllegalStateException("Unable to retrieve PayPal access token");
        }
    }

    private static class AccessTokenResponse {
        private String access_token;

        public String getAccessToken() {
            return access_token;
        }

        public void setAccessToken(String access_token) {
            this.access_token = access_token;
        }
    }

    private static class PaymentDetails {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
