package com.lifeos.module.task.service;

import com.lifeos.module.task.dto.TaskLabelRequest;
import com.lifeos.module.task.dto.TaskLabelResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Task Labels operations.
 */
public interface TaskLabelService {

    TaskLabelResponse createLabel(UUID userId, TaskLabelRequest request);

    List<TaskLabelResponse> getLabels(UUID userId);

    void deleteLabel(UUID userId, UUID id);
}
