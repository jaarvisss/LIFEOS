package com.lifeos.module.project.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.project.dto.ProjectRequest;
import com.lifeos.module.project.dto.ProjectResponse;

import java.util.UUID;

/**
 * Service Interface for Project Portfolio operations.
 */
public interface ProjectService {

    ProjectResponse createProject(UUID userId, ProjectRequest request);

    ProjectResponse getProjectById(UUID userId, UUID id);

    PagedResponse<ProjectResponse> getAllProjects(UUID userId, PaginationRequest paginationRequest);

    ProjectResponse updateProject(UUID userId, UUID id, ProjectRequest request);

    void deleteProject(UUID userId, UUID id);
}
