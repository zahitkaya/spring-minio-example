package com.example.minio.service;

import com.example.minio.util.MinioUtils;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MinioService {

    final MinioClient minioClient;
    final MinioUtils minioUtils;

    @Value("${minio.bucket.name}")
    String defaultBucketName;

    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /** *  List all object names in the bucket  * * @param bucketName  Bucket name  * @return */
    @SneakyThrows
    public List<String> listObjectNames(String bucketName) {
        List<String> listObjectNames = new ArrayList<>();
        if (minioUtils.bucketExists(bucketName)) {
            Iterable<Result<Item>> myObjects = minioUtils.listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                listObjectNames.add(item.objectName());
            }
        }else{
            listObjectNames.add(" Bucket does not exist ");
        }
        return listObjectNames;
    }

    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        boolean flag = minioUtils.bucketExists(bucketName);
        if (flag) {
            GetObjectArgs args = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build();
            return minioClient.getObject(args);
        }
        return null;
    }




}