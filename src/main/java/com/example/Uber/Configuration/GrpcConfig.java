package com.example.Uber.Configuration;


import com.example.Uber.service.impl.RideServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GrpcConfig {
    @Value("${grpc.server.port:9090}")
    private int grpcServerPort;

    private final RideServiceImpl rideServiceImpl;

    @PostConstruct
    public void startGrpcServer() throws Exception{
        System.out.println("server starting on "+grpcServerPort);
        Server server = ServerBuilder
                .forPort(grpcServerPort)
                .addService(rideServiceImpl)
                .build()
                .start();

        new Thread(()->{
            try{
                if(server != null){
                    server.awaitTermination();
                }
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("gRPC Server Interrupted");
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            System.out.println("Shutting down gRPC server");
            if(server != null) server.shutdown();
        }));
    }

}

