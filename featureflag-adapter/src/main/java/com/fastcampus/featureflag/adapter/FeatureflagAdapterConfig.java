package com.fastcampus.featureflag.adapter;

import dev.openfeature.contrib.providers.flagd.Config;
import dev.openfeature.contrib.providers.flagd.FlagdOptions;
import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@ComponentScan(
        basePackages =  {
                "com.fastcampus.featureflag.adapter",
        }
)
@Configuration
public class FeatureflagAdapterConfig {
    private final String flagdHost;
    private final String flagdPort;

    public FeatureflagAdapterConfig(Environment environment) {
        this.flagdHost = environment.getProperty("FEATUREFLAG_ENGINE_HOST") == null ? "localhost" : environment.getProperty("FEATUREFLAG_ENGINE_HOST");
        this.flagdPort = environment.getProperty("FEATUREFLAG_ENGINE_PORT") == null ? "8013" : environment.getProperty("FEATUREFLAG_ENGINE_PORT");
    }

    @Bean
    public Client featureflagAdapter() {
        FlagdProvider flagd = new FlagdProvider(
            FlagdOptions.builder()
                .resolverType(Config.Evaluator.RPC)
                .host(this.flagdHost)
                .port(Integer.parseInt(this.flagdPort))
                .tls(false)
                .maxCacheSize(1000)
                .retryBackoffMs(100)
                .deadline(3000)
                .cacheType(Config.LRU_CACHE)
                .build()
        );
        OpenFeatureAPI api = OpenFeatureAPI.getInstance();
        api.setProviderAndWait(flagd);
        return api.getClient();
    }
}

