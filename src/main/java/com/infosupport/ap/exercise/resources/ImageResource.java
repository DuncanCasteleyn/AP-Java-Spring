package com.infosupport.ap.exercise.resources;

import com.infosupport.ap.exercise.models.ClassRegistration;
import com.infosupport.ap.exercise.models.Presence;
import com.infosupport.ap.exercise.models.RegistrationImage;
import com.infosupport.ap.exercise.repositories.ClassRegistrationRepository;
import com.infosupport.ap.exercise.repositories.PresenceRepository;
import com.infosupport.ap.exercise.services.CognitiveServices;
import com.infosupport.ap.exercise.services.responses.DetectFaces;
import com.infosupport.ap.exercise.services.responses.GetPerson;
import com.infosupport.ap.exercise.services.responses.partial.Identification;
import com.infosupport.ap.exercise.services.responses.partial.IdentificationCandidates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by tomco on 7/03/2017.
 */
@RestController
@CrossOrigin
public class ImageResource {

    @Autowired
    ClassRegistrationRepository classRepository;

    @Autowired
    PresenceRepository presenceRepository;

    @Autowired
    CognitiveServices faceAPI;

    @CrossOrigin
    @RequestMapping(value = "/api/image", method = RequestMethod.POST, consumes = "application/json")
    public Presence uploadFile(@RequestBody RegistrationImage body) throws IOException {
        //Decoding
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte = decoder.decodeBuffer(body.getImage());

        //Detecting person in image
        DetectFaces detectFace = faceAPI.detect(imageByte);
        Identification identification = faceAPI.identify(Arrays.asList(detectFace.getFaceId()));
        IdentificationCandidates candidate = identification.getCandidates().get(0);
        GetPerson person = faceAPI.getPerson(candidate.getPersonId());

        //create presence and repository
        Presence presence = new Presence(body.getClassId(), person.getName(), new Date());
        presenceRepository.save(presence);

        return presence;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/classes", method = RequestMethod.POST, consumes = "application/json")
    public void addClass(@RequestBody @Valid ClassRegistration body) {
        System.out.println(body.getClassname());
        classRepository.save(body);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/classes/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public void update(@PathVariable Long id, @RequestBody @Valid ClassRegistration body) {
        body.setId(id);
        classRepository.save(body);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/classes", method = RequestMethod.GET)
    public Iterable<ClassRegistration> getAllClasses() {
        return classRepository.findAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/api/classes/{id}", method = RequestMethod.GET)
    public ClassRegistration getClassDetails(@PathVariable Long id) {
        return classRepository.findOne(id);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/classes/{id}/presence", method = RequestMethod.GET)
    public Iterable<Presence> getPresences(@PathVariable("id") Long classId) {
        return StreamSupport.stream(presenceRepository.findAll().spliterator(), false)
                .filter(p -> classId.equals(p.getClassId()))
                .collect(Collectors.toList());
    }
}
