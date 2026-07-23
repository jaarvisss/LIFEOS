package com.lifeos.module.project.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.project.dto.ProjectRequest;
import com.lifeos.module.project.dto.ProjectResponse;
import com.lifeos.module.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller for Project Portfolio Management.
 */
@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "Endpoints for managing projects, timelines, and goal linkage")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Create Project", description = "Creates a new project record.")
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(@Valid @RequestBody ProjectRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(projectService.createProject(userId, request), "Project created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Project by ID", description = "Retrieves project details by identifier.")
    public ResponseEntity<ApiResponse<ProjectResponse>> getProjectById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(projectService.getProjectById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Projects", description = "Retrieves paginated list of user projects.")
    public ResponseEntity<ApiResponse<PagedResponse<ProjectResponse>>> getAllProjects(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(projectService.getAllProjects(userId, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Project", description = "Updates project details.")
    public ResponseEntity<ApiResponse<ProjectResponse>> updateProject(
            @PathVariable UUID id,
            @Valid @RequestBody ProjectRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(projectService.updateProject(userId, id, request), "Project updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Project", description = "Soft-deletes a project record.")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        projectService.deleteProject(userId, id);
        return ResponseBuilder.ok(null, "Project deleted successfully");
    }
}
