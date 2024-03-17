package com.timekeeper.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordUpdate(
        @NotBlank String email,
        @NotBlank String beforePassword,
        @NotBlank String afterPassword
) {

}
