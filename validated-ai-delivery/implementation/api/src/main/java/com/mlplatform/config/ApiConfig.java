package com.mlplatform.config;

import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigRoot(phase = ConfigPhase.RUNTIME)
@ConfigMapping(prefix = "api")
public interface ApiConfig {

    @WithDefault("changeme-in-production")
    String key();
}
