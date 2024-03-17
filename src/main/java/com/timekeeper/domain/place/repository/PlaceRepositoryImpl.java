package com.timekeeper.domain.place.repository;

import com.timekeeper.domain.place.Place;
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
    public Optional<Place> findById(Long id) {
        return placeJpaRepository.findById(id);
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
