package com.timekeeper.adapter.in.request;


import jakarta.validation.constraints.NotBlank;

public record SignUp(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String position,
        @NotBlank String phoneNumber
) {

}
