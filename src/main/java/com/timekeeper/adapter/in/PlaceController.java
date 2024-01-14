package com.timekeeper.adapter.in;

import com.timekeeper.adapter.in.request.PlaceCreate;
import com.timekeeper.application.service.place.PlaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<String> createPlace(@Valid @RequestBody PlaceCreate placeCreate) {

        placeService.createPlace(placeCreate);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 추가되었습니다.");
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkPlace(@RequestParam("lat") double lat,
            @RequestParam("lon") double lon) {
        System.out.println(lat + "//" + lon);
        return ResponseEntity.status(HttpStatus.OK).body("조회완료.");
    }

}
