package com.timekeeper.adapter.in.request;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record PlaceCreate(
        @NotBlank String name,
        @NotBlank String address,
        List<PlacePoint> vertices
) {

}
