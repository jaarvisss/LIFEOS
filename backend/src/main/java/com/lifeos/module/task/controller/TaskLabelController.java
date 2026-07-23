package com.lifeos.module.task.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.task.dto.TaskLabelRequest;
import com.lifeos.module.task.dto.TaskLabelResponse;
import com.lifeos.module.task.service.TaskLabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Task Labels Management.
 */
@RestController
@RequestMapping("/api/v1/task-labels")
@RequiredArgsConstructor
@Tag(name = "Task Labels", description = "Endpoints for custom task labeling and categorization")
public class TaskLabelController {

    private final TaskLabelService taskLabelService;

    @PostMapping
    @Operation(summary = "Create Label", description = "Creates a new task label.")
    public ResponseEntity<ApiResponse<TaskLabelResponse>> createLabel(@Valid @RequestBody TaskLabelRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(taskLabelService.createLabel(userId, request), "Label created successfully");
    }

    @GetMapping
    @Operation(summary = "List Labels", description = "Retrieves user's task labels.")
    public ResponseEntity<ApiResponse<List<TaskLabelResponse>>> getLabels() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(taskLabelService.getLabels(userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Label", description = "Deletes a task label.")
    public ResponseEntity<ApiResponse<Void>> deleteLabel(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        taskLabelService.deleteLabel(userId, id);
        return ResponseBuilder.ok(null, "Label deleted successfully");
    }
}
