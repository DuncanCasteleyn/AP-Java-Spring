package com.infosupport.ap.exercise.resources;

import com.infosupport.ap.exercise.models.Presence;
import com.infosupport.ap.exercise.models.RegistrationImage;
import com.infosupport.ap.exercise.repositories.PresenceRepository;
import com.infosupport.ap.exercise.services.CognitiveServices;
import com.infosupport.ap.exercise.services.responses.DetectFaces;
import com.infosupport.ap.exercise.services.responses.GetPerson;
import com.infosupport.ap.exercise.services.responses.partial.Identification;
import com.infosupport.ap.exercise.services.responses.partial.IdentificationCandidates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

@RestController
public class ImageResource {

    @Autowired
    PresenceRepository presenceRepository;

    @Autowired
    CognitiveServices faceAPI;

    @RequestMapping(value = "/api/image", method = RequestMethod.POST, consumes = "application/json")
    public Presence uploadFile(@RequestBody RegistrationImage body) throws IOException {
        //Decoding
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(body.getImage());

        //Detecting person in image
        DetectFaces detectFace = faceAPI.detect(imageByte);
        Identification identification = faceAPI.identify(Arrays.asList(detectFace.getFaceId()));
        IdentificationCandidates candidate = Collections.max(identification.getCandidates(), Comparator.comparing(IdentificationCandidates::getConfidence));
        GetPerson person = faceAPI.getPerson(candidate.getPersonId());

        //create presence and repository
        Presence presence = new Presence(body.getClassId(), person.getName(), new Date());
        presenceRepository.save(presence);

        return presence;
    }


}
