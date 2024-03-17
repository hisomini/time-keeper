package com.timekeeper.domain.place.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PlaceDTO(
        @NotBlank Long id,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String vertices,
        @NotBlank LocalDateTime createDate,
        @NotBlank LocalDateTime updateDate,
        @NotBlank boolean isActive) {

}
