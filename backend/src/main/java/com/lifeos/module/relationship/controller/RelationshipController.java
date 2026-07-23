package com.lifeos.module.relationship.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.relationship.dto.MilestoneRequest;
import com.lifeos.module.relationship.dto.MilestoneResponse;
import com.lifeos.module.relationship.dto.RelationshipRequest;
import com.lifeos.module.relationship.dto.RelationshipResponse;
import com.lifeos.module.relationship.service.RelationshipService;
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

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for Relationship Dynamics and Milestones.
 */
@RestController
@RequestMapping("/api/v1/relationships")
@RequiredArgsConstructor
@Tag(name = "Relationship Dynamics", description = "Endpoints for relationship tracking, closeness metrics, and milestone dates")
public class RelationshipController {

    private final RelationshipService relationshipService;

    @PostMapping
    @Operation(summary = "Create Relationship", description = "Creates a new relationship record for a contact.")
    public ResponseEntity<ApiResponse<RelationshipResponse>> createRelationship(@Valid @RequestBody RelationshipRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(relationshipService.createRelationship(userId, request), "Relationship created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Relationship by ID", description = "Retrieves relationship details.")
    public ResponseEntity<ApiResponse<RelationshipResponse>> getRelationshipById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipService.getRelationshipById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Relationships", description = "Retrieves paginated list of relationships.")
    public ResponseEntity<ApiResponse<PagedResponse<RelationshipResponse>>> getAllRelationships(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipService.getAllRelationships(userId, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Relationship", description = "Updates relationship attributes.")
    public ResponseEntity<ApiResponse<RelationshipResponse>> updateRelationship(
            @PathVariable UUID id,
            @Valid @RequestBody RelationshipRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipService.updateRelationship(userId, id, request), "Relationship updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Relationship", description = "Soft-deletes a relationship record.")
    public ResponseEntity<ApiResponse<Void>> deleteRelationship(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        relationshipService.deleteRelationship(userId, id);
        return ResponseBuilder.ok(null, "Relationship deleted successfully");
    }

    @PostMapping("/milestones")
    @Operation(summary = "Add Milestone", description = "Adds a milestone event to a relationship.")
    public ResponseEntity<ApiResponse<MilestoneResponse>> addMilestone(@Valid @RequestBody MilestoneRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(relationshipService.addMilestone(userId, request), "Milestone added successfully");
    }

    @GetMapping("/{id}/milestones")
    @Operation(summary = "Get Relationship Milestones", description = "Retrieves all milestones for a specific relationship.")
    public ResponseEntity<ApiResponse<List<MilestoneResponse>>> getMilestones(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(relationshipService.getMilestones(userId, id));
    }
}
