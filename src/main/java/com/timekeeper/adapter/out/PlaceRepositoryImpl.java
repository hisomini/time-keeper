package com.timekeeper.adapter.out;

import com.timekeeper.domain.place.Place;
import com.timekeeper.domain.place.PlaceRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PlaceRepositoryImpl implements PlaceRepository {

    private final PlaceJpaRepository placeJpaRepository;

    @Override
    public Optional<Place> findByName(String name) {
        return placeJpaRepository.findByName(name);
    }

    @Override
    public List<Place> findAll() {
        return placeJpaRepository.findAll();
    }

    @Override
    public Place save(Place place) {
        return placeJpaRepository.save(place);
    }
}
