package com.pongo.taf.guice;

import com.google.inject.AbstractModule;
import com.pongo.taf.context.Context;
import io.cucumber.guice.ScenarioScoped;

public final class ProjectModule extends AbstractModule {
    @Override
    public void configure() {
        try {
            bind(Context.class).in(ScenarioScoped.class);
        } catch (Exception e) {
            addError(e.getMessage());
        }
    }
}
