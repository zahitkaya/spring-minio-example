package com.example.minio.util;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MinioUtils {

    final MinioClient minioClient;

    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        boolean found =
                minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

        return found;
    }

    @SneakyThrows
    public Iterable<Result<Item>> listObjects(String bucketName) {
        if (bucketExists(bucketName)) {

            return minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).recursive(true).build());
        }
        return null;
    }


}
