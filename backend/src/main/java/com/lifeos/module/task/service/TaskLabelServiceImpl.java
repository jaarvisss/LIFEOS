package com.lifeos.module.task.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.module.task.dto.TaskLabelRequest;
import com.lifeos.module.task.dto.TaskLabelResponse;
import com.lifeos.module.task.entity.TaskLabelEntity;
import com.lifeos.module.task.mapper.TaskLabelMapper;
import com.lifeos.module.task.repository.TaskLabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of TaskLabelService interface.
 */
@Service
@RequiredArgsConstructor
public class TaskLabelServiceImpl implements TaskLabelService {

    private final TaskLabelRepository taskLabelRepository;
    private final TaskLabelMapper taskLabelMapper;

    @Override
    @Transactional
    public TaskLabelResponse createLabel(UUID userId, TaskLabelRequest request) {
        TaskLabelEntity entity = taskLabelMapper.toEntity(request);
        entity.setUserId(userId);
        entity = taskLabelRepository.save(entity);
        return taskLabelMapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskLabelResponse> getLabels(UUID userId) {
        return taskLabelMapper.toDtoList(taskLabelRepository.findByUserId(userId));
    }

    @Override
    @Transactional
    public void deleteLabel(UUID userId, UUID id) {
        TaskLabelEntity label = taskLabelRepository.findById(id)
                .filter(l -> l.getUserId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("TaskLabel", "id", id));
        taskLabelRepository.delete(label);
    }
}
