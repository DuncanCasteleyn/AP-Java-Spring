package com.infosupport.ap.exercise.services;

import com.infosupport.ap.exercise.services.interceptors.HeaderRequestInterceptor;
import com.infosupport.ap.exercise.services.requests.IdentifyFacesRequest;
import com.infosupport.ap.exercise.services.responses.DetectFaces;
import com.infosupport.ap.exercise.services.responses.GetPerson;
import com.infosupport.ap.exercise.services.responses.partial.Identification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CognitiveServices {

    private final String BASEURL = "https://westus.api.cognitive.microsoft.com/face/v1.0/";
    private final String KEY_HEADER = "Ocp-Apim-Subscription-Key";
    private final String KEY = "6ff5ae598540491583a5ae9f11029697";
    private final String PERSON_GROUP = "ap-java-spring-boot-one";

    @Autowired
    RestTemplate restTemplate;

    public DetectFaces detect(byte[] encodedImage) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(KEY_HEADER, KEY);
        HttpEntity<?> requestEntity = new HttpEntity<Object>(encodedImage,requestHeaders);

        //IF return type is an Array, you need to wrap the return class like this :(
        ParameterizedTypeReference<List<DetectFaces>> type = new ParameterizedTypeReference<List<DetectFaces>>() {
        };

        ResponseEntity<List<DetectFaces>> response = restTemplate.exchange(BASEURL+"detect", HttpMethod.POST, requestEntity, type);
        System.out.println(response);
        return response.getBody().stream().findFirst().orElseThrow(() -> new RuntimeException("Multiple faces are not supported"));
    }

    public Identification identify(List<String> faceIds) {
        IdentifyFacesRequest request = new IdentifyFacesRequest(PERSON_GROUP, faceIds);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(KEY_HEADER, KEY);

        HttpEntity<?> requestEntity = new HttpEntity<Object>(request,requestHeaders);

        ParameterizedTypeReference<List<Identification>> type = new ParameterizedTypeReference<List<Identification>>() {
        };

        ResponseEntity<List<Identification>> response = restTemplate.exchange(BASEURL+"identify", HttpMethod.POST, requestEntity, type);
        System.out.println(response);
        return response.getBody().stream().findFirst().orElseThrow(() -> new RuntimeException("Multiple faces are not supported"));
    }

    // https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}/persons/{personId}
    public GetPerson getPerson(String personId) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(KEY_HEADER, KEY);

        HttpEntity<?> requestEntity = new HttpEntity<Object>(personId,requestHeaders);

        String url = BASEURL + "persongroups/"+ PERSON_GROUP+"/persons/"+personId;


        ResponseEntity<GetPerson> entity = restTemplate.getForEntity(url,GetPerson.class);
        System.out.println(entity);
        return entity.getBody();
    }

    @Bean
    RestTemplate getRestTemplate() {
        RestTemplate template = new RestTemplate();
        template.setInterceptors(Arrays.asList(new HeaderRequestInterceptor(KEY_HEADER, KEY)));
        return template;
    }
}
