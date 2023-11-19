package com.timekeeper.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String position
) {

}
