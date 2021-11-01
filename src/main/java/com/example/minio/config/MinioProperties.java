package com.example.minio.config;

import io.minio.MinioClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioProperties {

    /** *  It's a URL, domain name ,IPv4 perhaps IPv6 Address ") */
    @Value("${minio.url}")
    private String endpoint;


    /** * //"TCP/IP Port number " */
    @Value("${minio.port}")
    private Integer port;

    @Value("${minio.access.name}")
    private String accessKey;

    /** * //"secretKey It's the password for your account " */
    @Value("${minio.access.secret}")
    private String secretKey;

    /** * //" If it is true, It uses https instead of http, The default value is true" */
    private boolean secure;

    /** * //" Default bucket " */
    private String bucketName;

    /** *  The maximum size of the picture  */
    private long imageSize;

    /** *  Maximum size of other files  */
    private long fileSize;


    /** *  From the official website   Construction method , I just climbed the official website  （ The dog's head lives ） *  This is   The class that the client operates on  */
    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient =
                MinioClient.builder()
                        .credentials(accessKey, secretKey)
                        .endpoint(endpoint,port,false)
                        .build();
        return minioClient;
    }
}
