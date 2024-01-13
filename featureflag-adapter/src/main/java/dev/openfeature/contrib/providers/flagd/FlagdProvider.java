package dev.openfeature.contrib.providers.flagd;

import com.fastcampus.featureflag.adapter.metric.FeatureflagCounter;
import com.fastcampus.featureflag.adapter.openfeatureflag.cache.MyCache;
import dev.openfeature.contrib.providers.flagd.resolver.Resolver;
import dev.openfeature.contrib.providers.flagd.resolver.grpc.GrpcResolver;
import dev.openfeature.contrib.providers.flagd.resolver.process.InProcessResolver;
import dev.openfeature.sdk.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * OpenFeature provider for flagd.
 */
@Slf4j
@SuppressWarnings("PMD.TooManyStaticImports")
public class FlagdProvider extends EventProvider implements FeatureProvider {
    private static final String FLAGD_PROVIDER = "flagD Provider";

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Resolver flagResolver;
    private ProviderState state = ProviderState.NOT_READY;

    private EvaluationContext evaluationContext;


    /**
     * Create a new FlagdProvider instance with default options.
     */
    @Deprecated
    public FlagdProvider() {
        this(dev.openfeature.contrib.providers.flagd.FlagdOptions.builder().build(), null);
    }

    /**
     * Create a new FlagdProvider instance with customized options.
     *
     * @param options {@link dev.openfeature.contrib.providers.flagd.FlagdOptions} with
     */
    public FlagdProvider(final FlagdOptions options, FeatureflagCounter featureflagCounter) {
        switch (options.getResolverType()) {
            case IN_PROCESS:
                this.flagResolver = new InProcessResolver(options, this::setState);
                break;
            case RPC:
                this.flagResolver =
                        new GrpcResolver(options,
                                new MyCache(options.getCacheType(), options.getMaxCacheSize(), featureflagCounter),
                                this::getState,
                                this::setState);
                break;
            default:
                throw new IllegalStateException(
                        String.format("Requested unsupported resolver type of %s", options.getResolverType()));
        }
    }

    @Override
    public void initialize(EvaluationContext evaluationContext) throws Exception {
        this.evaluationContext = evaluationContext;
        this.flagResolver.init();
    }

    @Override
    public void shutdown() {
        try {
            this.flagResolver.shutdown();
        } catch (Exception e) {
            log.error("Error during shutdown {}", FLAGD_PROVIDER, e);
        }
    }

    @Override
    public ProviderState getState() {
        Lock l = this.lock.readLock();
        try {
            l.lock();
            return this.state;
        } finally {
            l.unlock();
        }
    }


    @Override
    public Metadata getMetadata() {
        return () -> FLAGD_PROVIDER;
    }

    @Override
    public ProviderEvaluation<Boolean> getBooleanEvaluation(String key, Boolean defaultValue, EvaluationContext ctx) {
        return this.flagResolver.booleanEvaluation(key, defaultValue, mergeContext(ctx));
    }

    @Override
    public ProviderEvaluation<String> getStringEvaluation(String key, String defaultValue, EvaluationContext ctx) {
        return this.flagResolver.stringEvaluation(key, defaultValue, mergeContext(ctx));
    }

    @Override
    public ProviderEvaluation<Double> getDoubleEvaluation(String key, Double defaultValue, EvaluationContext ctx) {
        return this.flagResolver.doubleEvaluation(key, defaultValue, mergeContext(ctx));
    }

    @Override
    public ProviderEvaluation<Integer> getIntegerEvaluation(String key, Integer defaultValue, EvaluationContext ctx) {
        return this.flagResolver.integerEvaluation(key, defaultValue, mergeContext(ctx));
    }

    @Override
    public ProviderEvaluation<Value> getObjectEvaluation(String key, Value defaultValue, EvaluationContext ctx) {
        return this.flagResolver.objectEvaluation(key, defaultValue, mergeContext(ctx));
    }

    private EvaluationContext mergeContext(final EvaluationContext clientCallCtx) {
        if (this.evaluationContext != null) {
            return evaluationContext.merge(clientCallCtx);
        }

        return clientCallCtx;
    }

    private void setState(ProviderState newState) {
        ProviderState oldState;
        Lock l = this.lock.writeLock();
        try {
            l.lock();
            oldState = this.state;
            this.state = newState;
        } finally {
            l.unlock();
        }
        this.handleStateTransition(oldState, newState);
    }

    private void handleStateTransition(ProviderState oldState, ProviderState newState) {
        // we got initialized
        if (ProviderState.NOT_READY.equals(oldState) && ProviderState.READY.equals(newState)) {
            // nothing to do, the SDK emits the events
            log.debug("Init completed");
            return;
        }
        // configuration changed
        if (ProviderState.READY.equals(oldState) && ProviderState.READY.equals(newState)) {
            log.debug("Configuration changed");
            ProviderEventDetails details = ProviderEventDetails.builder().message("configuration changed").build();
            this.emitProviderConfigurationChanged(details);
            return;
        }
        // there was an error
        if (ProviderState.READY.equals(oldState) && ProviderState.ERROR.equals(newState)) {
            log.debug("There has been an error");
            ProviderEventDetails details = ProviderEventDetails.builder().message("there has been an error").build();
            this.emitProviderError(details);
            return;
        }
        // we recover from an error
        if (ProviderState.ERROR.equals(oldState) && ProviderState.READY.equals(newState)) {
            log.debug("Recovered from error");
            ProviderEventDetails details = ProviderEventDetails.builder().message("recovered from error").build();
            this.emitProviderReady(details);
            this.emitProviderConfigurationChanged(details);
        }
    }
}
