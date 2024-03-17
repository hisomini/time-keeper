package com.timekeeper.domain.place.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PlaceListDTO(
        @NotBlank Long id,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank boolean isActive) {

}
