package dev.openfeature.contrib.providers.flagd.resolver.process.storage.connector.grpc;

import dev.openfeature.contrib.providers.flagd.resolver.process.storage.connector.grpc.GrpcResponseModel;
import dev.openfeature.flagd.sync.SyncService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
class GrpcStreamHandler implements StreamObserver<SyncService.SyncFlagsResponse> {
    private final BlockingQueue<dev.openfeature.contrib.providers.flagd.resolver.process.storage.connector.grpc.GrpcResponseModel> blockingQueue;

    GrpcStreamHandler(final BlockingQueue<dev.openfeature.contrib.providers.flagd.resolver.process.storage.connector.grpc.GrpcResponseModel> queue) {
        blockingQueue = queue;
    }

    @Override
    public void onNext(SyncService.SyncFlagsResponse syncFlagsResponse) {
        if (!blockingQueue.offer(new dev.openfeature.contrib.providers.flagd.resolver.process.storage.connector.grpc.GrpcResponseModel(syncFlagsResponse))) {
            log.warn("failed to write sync response to queue");
        }
    }

    @Override
    public void onError(Throwable throwable) {
        if (!blockingQueue.offer(new dev.openfeature.contrib.providers.flagd.resolver.process.storage.connector.grpc.GrpcResponseModel(throwable))) {
            log.warn("failed to write error response to queue");
        }
    }

    @Override
    public void onCompleted() {
        if (!blockingQueue.offer(new dev.openfeature.contrib.providers.flagd.resolver.process.storage.connector.grpc.GrpcResponseModel(true))) {
            log.warn("failed to write complete status to queue");
        }
    }
}
