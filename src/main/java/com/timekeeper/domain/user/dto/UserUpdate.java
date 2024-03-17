package com.timekeeper.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdate(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String phoneNumber,
        @NotBlank String position
) {


}
