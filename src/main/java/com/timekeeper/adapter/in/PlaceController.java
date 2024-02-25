package com.timekeeper.adapter.in;

import com.timekeeper.adapter.in.request.PlaceCreate;
import com.timekeeper.adapter.in.request.PlaceUpdate;
import com.timekeeper.application.service.place.PlaceService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
