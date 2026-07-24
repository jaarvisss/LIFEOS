package com.lifeos.module.project.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.project.dto.ProjectRequest;
import com.lifeos.module.project.dto.ProjectResponse;
import com.lifeos.module.project.entity.ProjectEntity;
import com.lifeos.module.project.mapper.ProjectMapper;
import com.lifeos.module.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of ProjectService interface.
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    @Transactional
    public ProjectResponse createProject(UUID userId, ProjectRequest request) {
        ProjectEntity project = projectMapper.toEntity(request);
        project.setUserId(userId);
        if (project.getStatus() == null || project.getStatus().isBlank()) {
            project.setStatus("ACTIVE");
        }
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(UUID userId, UUID id) {
        ProjectEntity project = projectRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        return projectMapper.toDto(project);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ProjectResponse> getAllProjects(UUID userId, PaginationRequest paginationRequest) {
        Page<ProjectEntity> page = projectRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(projectMapper::toDto));
    }

    @Override
    @Transactional
    public ProjectResponse updateProject(UUID userId, UUID id, ProjectRequest request) {
        ProjectEntity project = projectRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));

        projectMapper.updateEntityFromDto(request, project);
        if (project.getStatus() == null || project.getStatus().isBlank()) {
            project.setStatus("ACTIVE");
        }
        project = projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    @Transactional
    public void deleteProject(UUID userId, UUID id) {
        ProjectEntity project = projectRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
        project.markDeleted();
        projectRepository.save(project);
    }
}
