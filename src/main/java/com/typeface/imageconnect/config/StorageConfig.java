package com.typeface.imageconnect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class StorageConfig {

    @Value("${aws.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.secretKey}")
    private String secretKey;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;


    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(() -> AwsBasicCredentials.create(accessKeyId, secretKey))
                .build();
    }

}
