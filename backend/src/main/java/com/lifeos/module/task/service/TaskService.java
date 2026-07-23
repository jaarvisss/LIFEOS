package com.lifeos.module.task.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.task.dto.SubtaskRequest;
import com.lifeos.module.task.dto.SubtaskResponse;
import com.lifeos.module.task.dto.TaskRequest;
import com.lifeos.module.task.dto.TaskResponse;
import com.lifeos.module.task.dto.TaskStatusUpdateRequest;

import java.util.List;
import java.util.UUID;

/**
 * Service Interface for Task Execution & Lifecycle Management.
 */
public interface TaskService {

    TaskResponse createTask(UUID userId, TaskRequest request);

    TaskResponse getTaskById(UUID userId, UUID id);

    PagedResponse<TaskResponse> getAllTasks(UUID userId, String status, UUID projectId, PaginationRequest paginationRequest);

    TaskResponse updateTask(UUID userId, UUID id, TaskRequest request);

    TaskResponse updateStatus(UUID userId, UUID id, TaskStatusUpdateRequest request);

    void deleteTask(UUID userId, UUID id);

    SubtaskResponse addSubtask(UUID userId, SubtaskRequest request);

    List<SubtaskResponse> getSubtasks(UUID userId, UUID taskId);
}
