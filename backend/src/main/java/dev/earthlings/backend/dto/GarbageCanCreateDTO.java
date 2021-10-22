package dev.earthlings.backend.dto;

import lombok.Data;

@Data
public class GarbageCanCreateDTO {

    private double latitude;
    private double longitude;

    private String photoBase64;

}
