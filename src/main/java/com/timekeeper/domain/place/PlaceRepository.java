package com.timekeeper.domain.place;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository {

    Optional<Place> findByName(String name);

    Optional<Place> findById(Long id);

    List<Place> findAll();

    Place save(Place place);

}
