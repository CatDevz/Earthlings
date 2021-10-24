package dev.earthlings.backend.dto;

import lombok.Data;

/**
 * Create route data transfer object
 *
 * This is the only data the frontend sends us, and so we determine the rest of the data on our own.
 */
@Data
public class GarbageCanCreateDto {

    private double latitude;
    private double longitude;

    private String photoBase64;

}
