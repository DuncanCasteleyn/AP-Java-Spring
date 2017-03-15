package com.infosupport.ap.exercise.services.responses;

/**
 * Created by tomco on 22/01/2017.
 */
public class DetectFaces {
    private String faceId;

    public DetectFaces() {
    }

    public DetectFaces(String faceId) {
        this.faceId = faceId;
    }

    public String getFaceId() {
        return faceId;
    }
}
