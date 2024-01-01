package com.timekeeper.adapter.in.request;

import jakarta.validation.constraints.NotBlank;

public record UserPasswordUpdate(
        @NotBlank String email,
        @NotBlank String beforePassword,
        @NotBlank String afterPassword
) {

}
