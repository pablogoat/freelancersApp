package com.FreelancersBackend.api.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthenticationRequest {

    private String email;
    private String password;

    public AuthenticationRequest(@JsonProperty("email") String email,
                                 @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}
