package com.alejandro.controlgastos.integrations;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractMongoDbIntegrationTest {

    static final MongoDBContainer mongo = new MongoDBContainer("mongo:7.0");

    static final GenericContainer<?> redis = new GenericContainer<>("redis:7").withExposedPorts(6379);

    static {
        mongo.start();
        redis.start();
    } 

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        
        registry.add(
            "spring.data.mongodb.uri",
            () -> mongo.getReplicaSetUrl()
        );

        registry.add(
            "spring.data.redis.host",
            redis::getHost
        );

        registry.add(
            "spring.data.redis.port",
            () -> redis.getMappedPort(6379)
        );
    }
    
}
