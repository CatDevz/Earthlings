package dev.earthlings.backend.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GarbageCan implements Model {

    private String uuid;

    private double latitude;
    private double longitude;

    private String createdAt;
    private String updatedAt;

}
