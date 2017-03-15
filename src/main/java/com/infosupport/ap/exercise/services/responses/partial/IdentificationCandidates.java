package com.infosupport.ap.exercise.services.responses.partial;

/**
 * Created by tomco on 14/03/2017.
 */
public class IdentificationCandidates {
    private String personId;
    private double confidence;

    public IdentificationCandidates() {
    }

    public IdentificationCandidates(String personId, double confidence) {
        this.personId = personId;
        this.confidence = confidence;
    }

    public String getPersonId() {
        return personId;
    }

    public double getConfidence() {
        return confidence;
    }
}
