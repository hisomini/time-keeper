package com.timekeeper.application.service.place;

import com.timekeeper.adapter.in.request.PlaceCreate;
import com.timekeeper.domain.place.Place;
import com.timekeeper.domain.place.PlaceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Long createPlace(PlaceCreate placeCreate) {
        JSONArray result = new JSONArray(placeCreate.vertices());
        Place place = Place.builder()
                .name(placeCreate.name())
                .address(placeCreate.address())
                .vertices(result.toString())
                .build();
        placeRepository.save(place);
        return place.getId();
    }

    public boolean check(double lat, double lon) {
        List<Place> places = placeRepository.findAll();
        JSONArray result = new JSONArray(places);
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            JSONObject jsonObject = result.getJSONObject(i);
            double lonn = jsonObject.getDouble("lon");
            double latt = jsonObject.getDouble("lat");
            coordinates.add(new Coordinate(lonn, latt));
        }

        // GeometryFactory를 사용하여 직접 Polygon 생성
        GeometryFactory geometryFactory = new GeometryFactory();
        Polygon polygon = geometryFactory.createPolygon(coordinates.toArray(new Coordinate[0]));
        Point point = geometryFactory.createPoint(new Coordinate(lat, lon));
        polygon.contains(point);
        return true;
    }

}