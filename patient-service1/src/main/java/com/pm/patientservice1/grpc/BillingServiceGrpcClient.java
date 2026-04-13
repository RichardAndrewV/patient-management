package com.pm.patientservice1.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
//to make it spring manageable
public class BillingServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(BillingServiceGrpcClient.class);
//    @GrpcClient("billing-service")
    //nested class in the BillingServiceGrpc class that provides synchronous client call to the grpc server running in billing service i.e for every call it waits for its response to proceed further in execution
    //blocking stub holds the client call to server
    private BillingServiceGrpc.BillingServiceBlockingStub blockingStub;
    //localhost:9001/BillingService/CreatePatientAccount
    //in production aws:12345/BillingService/CreatePatientAccount aws->server address, 12345->server port
    //in these arguments we are adding the environment variables that points towards the grpc server that is the billing service , we are adding environment variables to the docker container so that we can connect to grpc server in billing service
    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String serverAddress,
                                    @Value("${billing.service.grpc.port:9001}") int serverPort) {
        log.info("Connecting to billing service gRPC service at {}:{}", serverAddress, serverPort);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);



    }
    public BillingResponse createBillingAccount(String PatientId, String name, String email) {
        BillingRequest request = BillingRequest.newBuilder().setName(name).setEmail(email).build();
        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response from billing service via GRPC: {}", response);
        return response;
    }

}