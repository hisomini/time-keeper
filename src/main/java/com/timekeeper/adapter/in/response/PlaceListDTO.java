package com.timekeeper.adapter.in.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PlaceListDTO(
        @NotBlank Long id,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank boolean isActive) {

}
