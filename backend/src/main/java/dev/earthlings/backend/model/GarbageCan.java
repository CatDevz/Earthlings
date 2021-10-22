package dev.earthlings.backend.model;

import lombok.Data;

@Data
public class GarbageCan {

    private String latitude;
    private String longitude;
    private String createdBy;
    private String lastUpdated;
    private String createdTime;
    private String photoUrl;
}
