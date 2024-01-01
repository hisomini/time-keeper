package com.timekeeper.adapter.in.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdate(
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String phoneNumber,
        @NotBlank String position
) {


}
