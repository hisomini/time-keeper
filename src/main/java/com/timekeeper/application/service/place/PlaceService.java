package com.timekeeper.application.service.place;

import com.timekeeper.adapter.in.request.PlaceCreate;
import com.timekeeper.adapter.in.request.PlaceUpdate;
import com.timekeeper.adapter.in.response.PlaceDTO;
import com.timekeeper.adapter.in.response.PlaceListDTO;
import com.timekeeper.domain.place.Place;
import com.timekeeper.domain.place.PlaceError;
import com.timekeeper.domain.place.PlaceRepository;
import com.timekeeper.shared.common.exception.error.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    public List<PlaceListDTO> getAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return places.stream()
                .map(place -> new PlaceListDTO(place.getId(), place.getName(), place.getAddress(),
                        place.isActive())).collect(
                        Collectors.toList());
    }

    public PlaceDTO findPlaceById(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PlaceError.PLACE_NOT_FOUND_ERROR));
        return PlaceDTO.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .vertices(place.getVertices())
                .createDate(place.getCreateDate())
                .updateDate(place.getUpdateDate())
                .isActive(place.isActive()).build();

    }

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

    public Long updatePlace(Long id, PlaceUpdate placeUpdate) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PlaceError.PLACE_NOT_FOUND_ERROR));
        JSONArray vertices = new JSONArray(placeUpdate.vertices());
        place.update(placeUpdate.name(), placeUpdate.address(), vertices.toString());
        placeRepository.save(place);
        return place.getId();
    }

    public Long activatePlace(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PlaceError.PLACE_NOT_FOUND_ERROR));
        place.activate();
        placeRepository.save(place);
        return place.getId();
    }

    public Long deactivatePlace(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PlaceError.PLACE_NOT_FOUND_ERROR));
        place.deactivate();
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
