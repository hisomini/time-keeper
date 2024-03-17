package com.timekeeper.domain.user.dto;


import jakarta.validation.constraints.NotBlank;

public record SignUp(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String position,
        @NotBlank String phoneNumber
) {

}
