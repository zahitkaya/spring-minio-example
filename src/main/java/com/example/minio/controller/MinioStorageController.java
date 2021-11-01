package com.example.minio.controller;

import com.example.minio.service.MinioService;
import io.minio.messages.Bucket;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MinioStorageController {

    final MinioService minioService;

    @GetMapping(path = "/buckets")
    public List<Bucket> listBuckets() {
        return minioService.getAllBuckets();
    }

    @GetMapping("listFiles")
    public List<String> getFiles(){
        return minioService.listObjectNames("certs");
    }

    @GetMapping("/download/{bucketName}")
    public void download(HttpServletResponse response, @PathVariable("bucketName") String bucketName, @RequestParam String objectName) {
        InputStream in = null;
        try {
            in = minioService.getObject(bucketName, objectName);
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(objectName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(in, response.getOutputStream());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}