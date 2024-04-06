package com.fastcampus.featureflag.adapter;


import com.fastcampus.featureflag.adapter.metric.FeatureflagCounter;
import dev.openfeature.contrib.providers.flagd.Config;
import dev.openfeature.contrib.providers.flagd.FlagdOptions;
import dev.openfeature.contrib.providers.flagd.FlagdProvider;
import dev.openfeature.sdk.Client;
import dev.openfeature.sdk.OpenFeatureAPI;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class FeatureflagAdapterConfig {
        private final Environment environment;
        private final MeterRegistry registry;

        @Bean
        public Client featureflagAdapter(){
                String flagdHost = environment.getProperty("FEATUREFLAG_ENGINE_HOST") == null ? "localhost" : environment.getProperty("FEATUREFLAG_ENGINE_HOST");
                String flagdPort = environment.getProperty("FEATUREFLAG_ENGINE_PORT") == null ? "8013" : environment.getProperty("FEATUREFLAG_ENGINE_PORT");
                String maxCacheSize = environment.getProperty("MAX_CACHE_SIZE") == null ? "100" : environment.getProperty("MAX_CACHE_SIZE");

                // "lru" or "custom"
                String cacheType = environment.getProperty("CACHE_TYPE") == null ? "lru" : environment.getProperty("CACHE_TYPE");

                FlagdProvider flagd = new FlagdProvider(
                        FlagdOptions.builder()
                                .resolverType(Config.Evaluator.RPC)
                                .host(flagdHost)
                                .port(Integer.parseInt(flagdPort))
                                .tls(false)
                                .maxCacheSize(Integer.parseInt(maxCacheSize))
                                .retryBackoffMs(100)
                                .deadline(3000)
                                .cacheType(cacheType)
                                .build(), featureflagCounter()
                );
                OpenFeatureAPI api = OpenFeatureAPI.getInstance();
                api.setProvider(flagd);
                return api.getClient();
        }

        public FeatureflagCounter featureflagCounter(){
                return FeatureflagCounter.standard(registry);
        }
}
