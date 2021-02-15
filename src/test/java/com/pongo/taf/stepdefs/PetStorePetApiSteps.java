package com.pongo.taf.stepdefs;

import com.github.javafaker.Faker;
import com.google.inject.Inject;
import com.pongo.taf.context.Context;
import com.pongo.taf.missions.bond.PetStorePetMission;
import com.pongo.taf.model.CreatePetRequest;
import com.pongo.taf.service.PetStorePetApiService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;


public class PetStorePetApiSteps {

    @Inject
    private PetStorePetApiService petStorePetApiService;
    private Context context;
    private PetStorePetMission petStorePetMission;

    @Inject
    public PetStorePetApiSteps(Context context, PetStorePetMission petStorePetMission) {
        this.context = context;
        this.petStorePetMission=petStorePetMission;
    }

    @Given("I create a new pet with a unique ID and the name {string} status {string}")
    public void createNewPet(String name, String status) {
        CreatePetRequest pet = petStorePetApiService.createAPet(name, status);
        Response response  = petStorePetMission.createAPetMission(pet);
        context.setResponse(response);

    }

    @Given("I create a new pet with a unique ID and name with status \"([^\"]*)\"$")
    public void createNewPetWithUniqueDetails(String status) {
        Faker faker = new Faker();
        String name = faker.name().fullName();

        //petStorePetApiService.createAPet();
    }

    @Then("I retrieve the created pet$")
    public void retrieveCreatedPet() {
        petStorePetApiService.retrieveThePetCreated();
    }

    @And("The response contains the field \"([^\"]*)\" with the correct value for the random \"([^\"]*)\"$")
    public void responseBodyHasCorrectValueForUniqueData(String field, String valueType) {
//
//        switch (valueType) {
//            case "ID":
//                assertionActions.theResponseHasTheCorrectIntValueInTheCorrectField(field, Integer.parseInt(SessionVariablesUtils.getPetId()));
//                break;
//            case "Name":
//                assertionActions.theResponseHasTheCorrectStringValueInTheCorrectField(field, SessionVariablesUtils.getName());
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + valueType);
//
//        }
    }

    @And("The response contains the field {string} with the value {string}")
    public void theResponseContainsTheFieldWithTheValue(String arg0, String arg1) {

    }
}

