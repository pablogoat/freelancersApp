package com.FreelancersBackend.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
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

    public PayPalService(WebClient.Builder webClientBuilder/*,
                         @Value("${paypal.client.id}") String clientId,
                         @Value("${paypal.client.secret}") String clientSecret,
                         @Value("${paypal.mode}") String mode,
                         @Value("${paypal.api.base-url}") String paypalBaseUrl*/
    ) {
        this.webClient = webClientBuilder.build();
        /*this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.mode = mode;
        this.paypalBaseUrl = paypalBaseUrl;*/
        System.out.println("mode: " + this.mode + "\nclientId: " + this.clientId + "\nclientSecret: " + this.clientSecret);
        //this.apiContext = new APIContext(this.clientId, this.clientSecret, this.mode);
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
            String rawResponse = webClient.post()
                    .uri(paypalBaseUrl + "/v1/oauth2/token")
                    .headers(headers -> headers.setBasicAuth(clientId, clientSecret))
                    .bodyValue("grant_type=client_credentials")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Raw Access Token Response: " + rawResponse);

            ObjectMapper mapper = new ObjectMapper();
            AccessTokenResponse response = mapper.readValue(rawResponse, AccessTokenResponse.class);

            System.out.println("Parsed Access Token: " + response.getAccessToken());
            return response.getAccessToken();
        } catch (WebClientResponseException e) {
            // Logowanie błędów
            System.err.println("Failed to get PayPal access token: " + e.getMessage());
            throw new IllegalStateException("Unable to retrieve PayPal access token");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class AccessTokenResponse {
        @JsonProperty("access_token")
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
