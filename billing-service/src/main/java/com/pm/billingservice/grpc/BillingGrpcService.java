//package com.pm.billingservice.grpc;
//
//import billing.BillingResponse;
//import billing.BillingServiceGrpc;
//import net.devh.boot.grpc.server.service.GrpcService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
////this grpc is used for starting the grpc server to receive and send messages between the services
////here this class needs to be managed by springboot lifecycle
//@GrpcService
////here the createBillingAccount is defined in Proto class by us, the spring generates other things in BillingServiceImplBase class by itself, and we are overriding that class method here
//public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
//    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);
//
//    @Override
//    // Stream Observer is used to send back and forth data between the client and the server as it can be used to update real time updates that is like chat messages sent back and forth, REST needs GET request multiple times as it is like single request single response model
//    public void createBillingAccount(billing.BillingRequest billingRequest, io.grpc.stub.StreamObserver<billing.BillingResponse> responseObserver) {
//        log.info("createBillingAccount request received{}", billingRequest.toString());
//        //BillingResponse is the return type object it is inside the Stream Observer itself
//         BillingResponse response= BillingResponse.newBuilder()
//                 .setAccountId("12345")
//                 .setStatus("SUCCESS")
//                 .build();
//
//         responseObserver.onNext(response);//send response to the client
//         responseObserver.onCompleted();//to say that the response cycle is finished for this.
//
//
//    }
//
//}
