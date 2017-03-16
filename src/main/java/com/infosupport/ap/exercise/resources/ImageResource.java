package com.infosupport.ap.exercise.resources;

import com.infosupport.ap.exercise.models.Presence;
import com.infosupport.ap.exercise.models.RegistrationImage;
import com.infosupport.ap.exercise.services.CognitiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.IOException;

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

        //END - TODO TASK 3B

        //TODO Task 4: create presence and repository, save and return
        return new Presence();
    }
}
