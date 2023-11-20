package com.timekeeper.user.dto;


import jakarta.validation.constraints.NotBlank;

public record SignUpDTO(
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank String name,
        @NotBlank String position
) {

}
