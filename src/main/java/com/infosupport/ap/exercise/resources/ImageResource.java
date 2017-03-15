package com.infosupport.ap.exercise.resources;

import com.infosupport.ap.exercise.models.Presence;
import com.infosupport.ap.exercise.models.RegistrationImage;
import com.infosupport.ap.exercise.services.CognitiveServices;
import com.infosupport.ap.exercise.services.responses.DetectFaces;
import com.infosupport.ap.exercise.services.responses.GetPerson;
import com.infosupport.ap.exercise.services.responses.Identification;
import com.infosupport.ap.exercise.services.responses.partial.IdentificationCandidates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class ImageResource {

    @Autowired
    CognitiveServices faceAPI;

    @CrossOrigin
    @RequestMapping(value = "/api/image", method = RequestMethod.POST, consumes = "application/json")
    public Presence uploadFile(@RequestBody RegistrationImage body) throws IOException {
        //Decoding
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(body.getImage());

        //TODO Task 3B - Detecting person in image
        DetectFaces detectFace = faceAPI.detect(imageByte);
        Identification identification = faceAPI.identify(Arrays.asList(detectFace.getFaceId()));
        IdentificationCandidates candidate = identification.getCandidates().get(0);
        GetPerson person = faceAPI.getPerson(candidate.getPersonId());

        //TODO Task 4: create presence and repository, save and return
        return null;
    }
}
