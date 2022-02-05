package com.youtubeclone.server.service.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.youtubeclone.server.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class S3ServiceImpl implements IFileService {

    public final AmazonS3Client amazonS3Client;

    @Value("${amazon.s3.bucketName}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        String fileNameExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String key = UUID.randomUUID().toString() + "." + fileNameExtension;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        try {
            amazonS3Client.putObject(bucketName, key, multipartFile.getInputStream(), metadata);
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An Exception occurred while uploading the file");
        }
        amazonS3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        String resourceUrl = amazonS3Client.getResourceUrl(bucketName, key);
        return resourceUrl;
    }
}
