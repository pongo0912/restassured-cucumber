package com.pongo.taf.hooks;

import com.pongo.taf.context.Context;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;

import com.google.inject.Inject;

@Slf4j
public class AfterTestTearDown {

    private Context context;

    @Inject
    public AfterTestTearDown(Context context) {
        this.context = context;
    }

    @After("@cleanUp")
    public void tearDown(Scenario scenario) {

    }

}


