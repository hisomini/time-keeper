package com.timekeeper.domain.place.controller;

import com.timekeeper.domain.place.dto.PlaceCreate;
import com.timekeeper.domain.place.dto.PlaceDTO;
import com.timekeeper.domain.place.dto.PlaceListDTO;
import com.timekeeper.domain.place.dto.PlaceUpdate;
import com.timekeeper.domain.place.service.PlaceService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/places")
@RestController
@RequiredArgsConstructor
@Slf4j
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<PlaceListDTO>> getAllPlaces() {
        List<PlaceListDTO> places = placeService.getAllPlaces();
        return ResponseEntity.status(HttpStatus.CREATED).body(places);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaceDTO> findPlaceById(@PathVariable Long id) {
        PlaceDTO place = placeService.findPlaceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(place);
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createPlace(
            @Valid @RequestBody PlaceCreate placeCreate) {
        Long placeId = placeService.createPlace(placeCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("placeId", placeId));
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkPlace(@RequestParam("lat") double lat,
            @RequestParam("lon") double lon) {
        return ResponseEntity.status(HttpStatus.OK).body("조회완료.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Long>> updatePlace(@PathVariable Long id,
            @Valid @RequestBody PlaceUpdate placeUpdate) {
        Long placeId = placeService.updatePlace(id, placeUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("placeId", placeId));
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<String> activatePlace(@PathVariable Long id) {
        placeService.activatePlace(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivatePlace(@PathVariable Long id) {
        placeService.deactivatePlace(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
