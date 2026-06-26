package com.example.Uber.Client;

import com.example.Uber.RideNotificationRequest;
import com.example.Uber.RideNotificationResponse;
import com.example.Uber.RideNotificationServiceGrpc;
import com.example.Uber.RideServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrpcClient {

    @Value("${grpc.server.port:9090}")
    private int grpcServerPort;

    @Value("${grpc.server.host:localhost}")
    private String gRPCServerHost;


    private ManagedChannel channel;

    private RideNotificationServiceGrpc.RideNotificationServiceBlockingStub rideNotificationServiceBlockingStub;


    @PostConstruct
    public void init(){
        channel = ManagedChannelBuilder.forAddress(gRPCServerHost,grpcServerPort)
                .usePlaintext()
                .build();

        rideNotificationServiceBlockingStub = RideNotificationServiceGrpc.newBlockingStub(channel);

    }

    public boolean notifyDriversForNewRide(Double pickUpLocationLattitude, Double pickUpLocationLongitude, Double dropOffLocationLattitude, Double dropOffLocationLongitude, Integer bookingId, List<Integer>driverIds){
        RideNotificationRequest request = RideNotificationRequest.newBuilder()
                .setPickUpLocationLattitude(pickUpLocationLattitude)
                .setPickUpLocationLongitude(pickUpLocationLongitude)
                .setDropoffLocationLattitude(dropOffLocationLattitude)
                .setDropoffLocationLongitude(dropOffLocationLongitude)
                .setBookingId(bookingId)
                .addAllDriverIds(driverIds)
                .build();

        RideNotificationResponse response = rideNotificationServiceBlockingStub.notifyDriversForNewRide(request);
        return response.getSuccess();
    }


}
