package com.timekeeper.adapter.in.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record PlaceUpdate(
        @NotBlank String name,
        @NotBlank String address,
        List<PlacePoint> vertices
) {

}
