package com.typeface.imageconnect.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;


import java.io.File;

@Service
public class StorageService {

    private final String bucketName;
    private final S3Client s3Client;


    public StorageService(@Value("${aws.s3.bucketName}") String bucketName, S3Client s3Client) {
        this.bucketName = bucketName;
        this.s3Client = s3Client;
    }

    public String uploadImage(byte[] fileContent, String fileName) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .acl("public-read")
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(fileContent));

        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }


}
