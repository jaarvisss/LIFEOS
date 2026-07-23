package com.lifeos.module.task.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.task.dto.SubtaskRequest;
import com.lifeos.module.task.dto.SubtaskResponse;
import com.lifeos.module.task.dto.TaskRequest;
import com.lifeos.module.task.dto.TaskResponse;
import com.lifeos.module.task.dto.TaskStatusUpdateRequest;
import com.lifeos.module.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Task Management.
 */
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "Endpoints for task creation, subtasks, priorities, and status execution")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create Task", description = "Creates a new task record.")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody TaskRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(taskService.createTask(userId, request), "Task created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Task by ID", description = "Retrieves task details.")
    public ResponseEntity<ApiResponse<TaskResponse>> getTaskById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(taskService.getTaskById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Tasks", description = "Retrieves paginated tasks with optional status and project filters.")
    public ResponseEntity<ApiResponse<PagedResponse<TaskResponse>>> getAllTasks(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "projectId", required = false) UUID projectId,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(taskService.getAllTasks(userId, status, projectId, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Task", description = "Updates task attributes.")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody TaskRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(taskService.updateTask(userId, id, request), "Task updated successfully");
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update Task Status", description = "Updates task status (TODO, IN_PROGRESS, BLOCKED, COMPLETED).")
    public ResponseEntity<ApiResponse<TaskResponse>> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody TaskStatusUpdateRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(taskService.updateStatus(userId, id, request), "Task status updated");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Task", description = "Soft-deletes a task.")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        taskService.deleteTask(userId, id);
        return ResponseBuilder.ok(null, "Task deleted successfully");
    }

    @PostMapping("/subtasks")
    @Operation(summary = "Add Subtask", description = "Adds a subtask to a parent task.")
    public ResponseEntity<ApiResponse<SubtaskResponse>> addSubtask(@Valid @RequestBody SubtaskRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(taskService.addSubtask(userId, request), "Subtask created successfully");
    }

    @GetMapping("/{id}/subtasks")
    @Operation(summary = "Get Subtasks", description = "Retrieves subtasks for a task.")
    public ResponseEntity<ApiResponse<List<SubtaskResponse>>> getSubtasks(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(taskService.getSubtasks(userId, id));
    }
}
