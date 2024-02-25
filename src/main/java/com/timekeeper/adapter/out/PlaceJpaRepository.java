package com.timekeeper.adapter.out;

import com.timekeeper.domain.place.Place;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceJpaRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByName(String name);


}
