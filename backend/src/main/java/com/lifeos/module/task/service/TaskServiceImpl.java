package com.lifeos.module.task.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.task.dto.SubtaskRequest;
import com.lifeos.module.task.dto.SubtaskResponse;
import com.lifeos.module.task.dto.TaskRequest;
import com.lifeos.module.task.dto.TaskResponse;
import com.lifeos.module.task.dto.TaskStatusUpdateRequest;
import com.lifeos.module.task.entity.SubtaskEntity;
import com.lifeos.module.task.entity.TaskEntity;
import com.lifeos.module.task.mapper.TaskMapper;
import com.lifeos.module.task.repository.SubtaskRepository;
import com.lifeos.module.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of TaskService interface.
 */
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SubtaskRepository subtaskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(UUID userId, TaskRequest request) {
        TaskEntity task = taskMapper.toEntity(request);
        task.setUserId(userId);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(UUID userId, UUID id) {
        TaskEntity task = taskRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        return taskMapper.toDto(task);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<TaskResponse> getAllTasks(UUID userId, String status, UUID projectId, PaginationRequest paginationRequest) {
        Page<TaskEntity> page;
        if (projectId != null) {
            page = taskRepository.findByUserIdAndProjectIdAndIsDeletedFalse(userId, projectId, paginationRequest.toPageable());
        } else if (status != null && !status.isBlank()) {
            page = taskRepository.findByUserIdAndStatusAndIsDeletedFalse(userId, status, paginationRequest.toPageable());
        } else {
            page = taskRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        }
        return PagedResponse.from(page.map(taskMapper::toDto));
    }

    @Override
    @Transactional
    public TaskResponse updateTask(UUID userId, UUID id, TaskRequest request) {
        TaskEntity task = taskRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        taskMapper.updateEntityFromDto(request, task);
        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    @Transactional
    public TaskResponse updateStatus(UUID userId, UUID id, TaskStatusUpdateRequest request) {
        TaskEntity task = taskRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));

        task.setStatus(request.status());
        if ("COMPLETED".equalsIgnoreCase(request.status())) {
            task.setCompletedAt(Instant.now());
        } else {
            task.setCompletedAt(null);
        }

        task = taskRepository.save(task);
        return taskMapper.toDto(task);
    }

    @Override
    @Transactional
    public void deleteTask(UUID userId, UUID id) {
        TaskEntity task = taskRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
        task.markDeleted();
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public SubtaskResponse addSubtask(UUID userId, SubtaskRequest request) {
        taskRepository.findByIdAndUserIdAndIsDeletedFalse(request.taskId(), userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", request.taskId()));

        SubtaskEntity subtask = taskMapper.toEntity(request);
        subtask = subtaskRepository.save(subtask);
        return taskMapper.toDto(subtask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubtaskResponse> getSubtasks(UUID userId, UUID taskId) {
        taskRepository.findByIdAndUserIdAndIsDeletedFalse(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));

        return taskMapper.toSubtaskDtoList(subtaskRepository.findByTaskIdOrderBySequenceOrderAsc(taskId));
    }
}
