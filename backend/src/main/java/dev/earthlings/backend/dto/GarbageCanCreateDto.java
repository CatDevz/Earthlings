package dev.earthlings.backend.dto;

import lombok.Data;

@Data
public class GarbageCanCreateDto {

    private double latitude;
    private double longitude;

    private String photoBase64;

}
