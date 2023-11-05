package com.timekeeper.user.command.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId implements Serializable {

    @Column(name = "use_id")
    private String id;


    public UserId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
