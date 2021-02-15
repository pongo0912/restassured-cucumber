package com.pongo.taf.hooks;

import com.google.inject.Inject;
import com.pongo.taf.context.Context;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreTestSetUp {

    private Context context;

    @Inject
    public PreTestSetUp(Context context) {
        this.context = context;
    }

    @Before(value = "@token")
    public void setUp(Scenario scenario) {
//        setLogParams(scenario);
        envSetUp();
    }

    private void envSetUp() {
        log.info("Setting up environment");
//        Env env = Env.env();
//        setCommonEnvValues(env);
    }

    private void setCommonEnvValues() {
//        context.setEnvPath(env.config.getString("da-env-path"));
//        context.setEnvVersion(env.config.getString("env-version"));
//        context.setEnvName(env.config.getString("env"));
    }

//    private void setLogParams(Scenario scenario) {
//        MDC.put("-FEATURE-", scenario.getName());
//        String[] featureFilePaths = scenario.getId().split("/");
//        MDC.put("-FEATURE-FILE-", featureFilePaths[featureFilePaths.length - 1]);
//    }


}
