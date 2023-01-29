package com.bicycle.project.oauthlogin.domain.s3;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public List<String> upload(List<MultipartFile> uploadFile, String dirName) {
        List<String> imageUrls = new ArrayList<>();
        for(MultipartFile file : uploadFile ) {
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String randomName = UUID.randomUUID().toString() + fileType; // 파일 중복되지 않게 고유식별자 생성
                        //중복될일 찾아보기
            String fileName = dirName + "/" + randomName;
            imageUrls.add(putS3(file, fileName));

        }
        return imageUrls;
    }

    // s3에 이미지 업로드 1개
    public String upload1(MultipartFile uploadFile, String dirName) {

            String fileType = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
            String randomName = UUID.randomUUID().toString() + fileType; // 파일 중복되지 않게 고유식별자 생성
            //중복될일 찾아보기
            String fileName = dirName + "/" + randomName;
            String imgUrl = putS3(uploadFile, fileName);


        return imgUrl;
    }
    public void deleteFileFromS3(String key) {
        //key는 경로, 파일이름 풀로 ex) static/test.txt
        deleteFile(key);
    }



    private String putS3(MultipartFile uploadFile, String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(uploadFile.getContentType());
        metadata.setContentLength(uploadFile.getSize());
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead));

        }catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        try {
            amazonS3Client.deleteObject(deleteObjectRequest);
        }catch(AmazonServiceException e) {
            e.printStackTrace();
        }catch (SdkClientException e) {
            e.printStackTrace();
        }
    }


}