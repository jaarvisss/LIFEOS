package com.lifeos.module.storage.service;

import com.lifeos.common.enums.ErrorCode;
import com.lifeos.common.exception.BusinessException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Local File System Storage Implementation.
 */
@Service
@Primary
public class LocalStorageService implements StorageService {

    private final Path storageLocation = Paths.get("uploads").toAbsolutePath().normalize();

    public LocalStorageService() {
        try {
            Files.createDirectories(storageLocation);
        } catch (Exception ignored) {
        }
    }

    @Override
    public String uploadFile(UUID userId, String filename, String mimeType, byte[] content) {
        try {
            String storageKey = userId + "/" + UUID.randomUUID() + "_" + filename;
            Path targetPath = storageLocation.resolve(storageKey);
            Files.createDirectories(targetPath.getParent());
            Files.write(targetPath, content);
            return storageKey;
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to store file: " + ex.getMessage());
        }
    }

    @Override
    public byte[] downloadFile(String storageKey) {
        try {
            Path filePath = storageLocation.resolve(storageKey).normalize();
            return Files.readAllBytes(filePath);
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, "Failed to read file: " + ex.getMessage());
        }
    }

    @Override
    public void deleteFile(String storageKey) {
        try {
            Path filePath = storageLocation.resolve(storageKey).normalize();
            Files.deleteIfExists(filePath);
        } catch (Exception ignored) {
        }
    }
}
