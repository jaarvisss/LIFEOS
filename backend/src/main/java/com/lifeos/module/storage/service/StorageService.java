package com.lifeos.module.storage.service;

import java.io.InputStream;
import java.util.UUID;

/**
 * Abstract Storage Service Interface for Document & File Storage.
 */
public interface StorageService {

    String uploadFile(UUID userId, String filename, String mimeType, byte[] content);

    byte[] downloadFile(String storageKey);

    void deleteFile(String storageKey);
}
