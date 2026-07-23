package com.lifeos.module.storage.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * AWS S3 / MinIO Compatible Storage Implementation wrapper.
 */
@Service("s3StorageService")
public class S3StorageService implements StorageService {

    @Override
    public String uploadFile(UUID userId, String filename, String mimeType, byte[] content) {
        return "s3://" + userId + "/" + UUID.randomUUID() + "_" + filename;
    }

    @Override
    public byte[] downloadFile(String storageKey) {
        return new byte[0];
    }

    @Override
    public void deleteFile(String storageKey) {
    }
}
