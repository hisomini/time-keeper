package com.timekeeper.domain.place;

import com.timekeeper.shared.common.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "place")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String vertices;

    @Builder
    private Place(String name, String address, String vertices) {
        this.name = name;
        this.address = address;
        this.vertices = vertices;

    }

    public void update(String name, String address, String vertices) {
        this.name = name;
        this.address = address;
        this.vertices = vertices;
        setUpdateDate();
    }

    public void activate() {
        setActive(true);
    }

    public void deactivate() {
        setActive(false);
    }
}
