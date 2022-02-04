package com.youtubeclone.server.service.impl;

import com.youtubeclone.server.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class S3ServiceImpl implements IFileService {
    
    @Override
    public String uploadFile(MultipartFile multipartFile) {
        return null;
    }
}
