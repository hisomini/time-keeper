package com.timekeeper.domain.place.dto;

import com.timekeeper.domain.place.PlacePoint;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record PlaceUpdate(
        @NotBlank String name,
        @NotBlank String address,
        List<PlacePoint> vertices
) {

}
