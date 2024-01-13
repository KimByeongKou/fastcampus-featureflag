package com.fastcampus.featureflag.adapter.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

public class FeatureflagCounter {
    private final Counter cacheHitCounter;
    private final Counter cacheMissCounter;

    private FeatureflagCounter(Counter cacheHitCounter, Counter cacheMissCounter) {
        this.cacheHitCounter = cacheHitCounter;
        this.cacheMissCounter = cacheMissCounter;
    }

    public static FeatureflagCounter standard( MeterRegistry registry) {
        registry.config().commonTags("app", "fastcampus-featureflag");

        return new FeatureflagCounter(
            buildCacheHitCounter(registry),
            buildCacheMissCounter(registry)
        );
    }

    private static Counter buildCacheHitCounter(MeterRegistry registry) {
        String counterName = "cache_counter.hit";
        return registry.counter(counterName);
    }

    private static Counter buildCacheMissCounter(MeterRegistry registry) {
        String counterName = "cache_counter.miss";
        return registry.counter(counterName);
    }

    public void incrementCacheHit() {
        cacheHitCounter.increment();
    }

    public void incrementCacheMiss() {
        cacheMissCounter.increment();
    }
}