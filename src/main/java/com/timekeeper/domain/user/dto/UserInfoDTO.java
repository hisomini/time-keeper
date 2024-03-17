package com.timekeeper.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserInfoDTO(
        @NotBlank Long id,
        @NotBlank String email,
        @NotBlank String name,
        @NotBlank String position,
        @NotBlank String phoneNumber) {

}
