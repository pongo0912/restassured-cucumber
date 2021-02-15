package com.pongo.taf.missions;

import com.pongo.taf.context.Context;
import com.pongo.taf.model.CreatePetRequest;
import com.pongo.taf.utils.LogFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import com.google.inject.Inject;
import static io.restassured.RestAssured.given;

@Slf4j
public class PetStorePetMission {

    private Context context;
    private Response response;

    private static final String PET_ENDPOINT = "pet/";

    @Inject
    public PetStorePetMission(Context context) {
        this.context = context;
    }

    //create a new pet
    public Response createAPetMission(CreatePetRequest createPetRequest) {
            log.info("create a new pet {}");
        RequestSpecification request = given()
                        .relaxedHTTPSValidation()
                        .filter(new LogFilter())
                        .header("ContentType", "JSON")
                        .body(createPetRequest)
                        .baseUri("https://petstore.swagger.io/v2/" + PET_ENDPOINT)
                        .when();
        context.setRequest(request);
        return request.post();
    }

    //this is for retrieving the newly created pet
    public void retrieveThePetCreated() {

    }

    private Response getResponse() {
        return response;
    }

    private void setResponse(Response response) {
        this.response = response;
    }
}
