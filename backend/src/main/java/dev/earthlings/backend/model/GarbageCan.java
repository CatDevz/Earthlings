package dev.earthlings.backend.model;

import lombok.Data;

@Data
public class GarbageCan {

    private Double latitude;
    private Double longitude;
    private String address;

    private String photoUrl;

    private String createdAt;
    private String updatedAt;

}
