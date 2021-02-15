package com.pongo.taf.service;

import com.google.inject.Inject;
import com.pongo.taf.context.Context;
import com.pongo.taf.model.CreatePetRequest;
import groovy.util.logging.Slf4j;
import io.restassured.response.Response;
import java.util.UUID;

@Slf4j
public class PetStorePetApiService {

    private Response response;

    public CreatePetRequest createAPet(String name, String status) {
        String randomId = UUID.randomUUID().toString();
        CreatePetRequest pet = new CreatePetRequest();
        pet.setId(randomId);
        pet.setStatus(status);
        pet.setName(name);
        return pet;
    }


    //this is for retrieving the newly created pet
    public void retrieveThePetCreated() {

    }

}
