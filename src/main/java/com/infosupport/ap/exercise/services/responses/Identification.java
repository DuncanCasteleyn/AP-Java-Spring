package com.infosupport.ap.exercise.services.responses;

import com.infosupport.ap.exercise.services.responses.partial.IdentificationCandidates;

import java.util.List;

/**
 * Created by tomco on 14/03/2017.
 */
public class Identification {
    private String faceId;
    private List<IdentificationCandidates> candidates;

    public Identification() {
    }

    public Identification(String faceId, List<IdentificationCandidates> candidates) {
        this.faceId = faceId;
        this.candidates = candidates;
    }

    public String getFaceId() {
        return faceId;
    }

    public List<IdentificationCandidates> getCandidates() {
        return candidates;
    }
}
