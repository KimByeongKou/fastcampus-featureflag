package dev.openfeature.contrib.providers.flagd;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nonnull;

import static dev.openfeature.contrib.providers.flagd.Config.*;

/**
 * FlagdOptions is a builder to build flagd provider options.
 */
@Builder
@Getter
@SuppressWarnings("PMD.TooManyStaticImports")
public class FlagdOptions {

    /**
     * flagd resolving type.
     */
    @Builder.Default
    private dev.openfeature.contrib.providers.flagd.Config.Evaluator resolverType = DEFAULT_RESOLVER_TYPE;

    /**
     * flagd connection host.
     */
    @Builder.Default
    private String host = fallBackToEnvOrDefault(HOST_ENV_VAR_NAME, DEFAULT_HOST);

    /**
     * flagd connection port.
     */
    @Builder.Default
    private int port = Integer.parseInt(Config.fallBackToEnvOrDefault(PORT_ENV_VAR_NAME, DEFAULT_PORT));

    /**
     * Use TLS connectivity.
     */
    @Builder.Default
    private boolean tls = Boolean.parseBoolean(Config.fallBackToEnvOrDefault(TLS_ENV_VAR_NAME, DEFAULT_TLS));

    /**
     * TLS certificate overriding if TLS connectivity is used.
     */
    @Builder.Default
    private String certPath = Config.fallBackToEnvOrDefault(SERVER_CERT_PATH_ENV_VAR_NAME, null);

    /**
     * Unix socket path to flagd.
     */
    @Builder.Default
    private String socketPath = Config.fallBackToEnvOrDefault(SOCKET_PATH_ENV_VAR_NAME, null);

    /**
     * Cache type to use. Supports - lru, disabled.
     */
    @Builder.Default
    private String cacheType = Config.fallBackToEnvOrDefault(CACHE_ENV_VAR_NAME, DEFAULT_CACHE);

    /**
     * Max cache size.
     */
    @Builder.Default
    private int maxCacheSize = Config.fallBackToEnvOrDefault(MAX_CACHE_SIZE_ENV_VAR_NAME, DEFAULT_MAX_CACHE_SIZE);

    /**
     * Max event stream connection retries.
     */
    @Builder.Default
    private int maxEventStreamRetries =
            Config.fallBackToEnvOrDefault(MAX_EVENT_STREAM_RETRIES_ENV_VAR_NAME, DEFAULT_MAX_EVENT_STREAM_RETRIES);

    /**
     * Backoff interval in milliseconds.
     */
    @Builder.Default
    private int retryBackoffMs =
            Config.fallBackToEnvOrDefault(BASE_EVENT_STREAM_RETRY_BACKOFF_MS_ENV_VAR_NAME, BASE_EVENT_STREAM_RETRY_BACKOFF_MS);


    /**
     * Connection deadline in milliseconds.
     * For RPC resolving, this is the deadline to connect to flagd for flag evaluation.
     * For in-process resolving, this is the deadline for sync stream termination.
     */
    @Builder.Default
    private int deadline = Config.fallBackToEnvOrDefault(DEADLINE_MS_ENV_VAR_NAME, DEFAULT_DEADLINE);

    /**
     * Selector to be used with flag sync gRPC contract.
     **/
    @Builder.Default
    private String selector = Config.fallBackToEnvOrDefault(SOURCE_SELECTOR_ENV_VAR_NAME, null);

    /**
     * File source of flags to be used by offline mode.
     * Setting this enables the offline mode of the in-process provider.
     */
    private String offlineFlagSourcePath;

    /**
     * Flagd option to state the offline mode. Only get set with offlineFlagSourcePath.
     */
    private boolean isOffline;

    /**
     * Inject OpenTelemetry for the library runtime. Providing sdk will initiate distributed tracing for flagd grpc
     * connectivity.
     */
    private OpenTelemetry openTelemetry;

    /**
     * Overload default lombok builder.
     */
    public static class FlagdOptionsBuilder {

        /**
         * File source of flags to be used by offline mode.
         * Setting this enables the offline mode of the in-process provider.
         */
        public FlagdOptionsBuilder offlineFlagSourcePath(@Nonnull final String offlineFlagSourcePath) {
            this.isOffline = true;
            this.offlineFlagSourcePath = offlineFlagSourcePath;

            return this;
        }

        // Remove the public access as this needs to be connected to offlineFlagSourcePath
        @SuppressWarnings({"PMD.UnusedFormalParameter", "PMD.UnusedPrivateMethod"})
        private FlagdOptionsBuilder isOffline(final boolean isOffline) {
            return this;
        }

        /**
         * Enable OpenTelemetry instance extraction from GlobalOpenTelemetry. Note that, this is only useful if global
         * configurations are registered.
         */
        public FlagdOptionsBuilder withGlobalTelemetry(final boolean b) {
            this.openTelemetry = GlobalOpenTelemetry.get();

            return this;
        }
    }
}