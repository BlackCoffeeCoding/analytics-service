package org.blackcoffeecoding.analytics.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.blackcoffeecoding.grpc.AnalyticsServiceGrpc;
import org.blackcoffeecoding.grpc.DeviceRatingRequest;
import org.blackcoffeecoding.grpc.DeviceRatingResponse;

import java.util.Random;

@GrpcService // <-- Эта аннотация делает класс gRPC сервером
public class AnalyticsServiceImpl extends AnalyticsServiceGrpc.AnalyticsServiceImplBase {

    @Override
    public void calculateDeviceRating(DeviceRatingRequest request, StreamObserver<DeviceRatingResponse> responseObserver) {
        // Имитация бурной деятельности
        int score = new Random().nextInt(100);
        String verdict = score > 50 ? "TOP DEVICE" : "AVERAGE";

        if ("CRASH".equalsIgnoreCase(request.getCategory())) { // Для тестов ошибок
            throw new RuntimeException("Analytics system failure!");
        }

        DeviceRatingResponse response = DeviceRatingResponse.newBuilder()
                .setDeviceId(request.getDeviceId())
                .setRatingScore(score)
                .setVerdict(verdict)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}