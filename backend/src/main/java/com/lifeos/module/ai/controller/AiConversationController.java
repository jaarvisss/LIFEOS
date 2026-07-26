package com.lifeos.module.ai.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.ai.dto.AiConversationRequest;
import com.lifeos.module.ai.dto.AiConversationResponse;
import com.lifeos.module.ai.dto.AiMessageRequest;
import com.lifeos.module.ai.dto.AiMessageResponse;
import com.lifeos.module.ai.service.AiConversationService;
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
 * REST Controller for AI Conversation & Messaging Platform.
 */
@RestController
@RequestMapping("/api/v1/ai/history")
@RequiredArgsConstructor
@Tag(name = "AI Conversation Engine", description = "Endpoints for AI conversation history, system instructions, and interactive chat")
public class AiConversationController {

    private final AiConversationService aiConversationService;

    @PostMapping
    @Operation(summary = "Create AI Conversation", description = "Initializes a new AI assistant chat thread.")
    public ResponseEntity<ApiResponse<AiConversationResponse>> createConversation(@Valid @RequestBody AiConversationRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(aiConversationService.createConversation(userId, request), "Conversation created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Conversation by ID", description = "Retrieves conversation details.")
    public ResponseEntity<ApiResponse<AiConversationResponse>> getConversationById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiConversationService.getConversationById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Conversations", description = "Retrieves paginated AI conversation threads.")
    public ResponseEntity<ApiResponse<PagedResponse<AiConversationResponse>>> getConversations(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiConversationService.getConversations(userId, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Conversation", description = "Updates conversation title or system instruction.")
    public ResponseEntity<ApiResponse<AiConversationResponse>> updateConversation(
            @PathVariable UUID id,
            @Valid @RequestBody AiConversationRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiConversationService.updateConversation(userId, id, request), "Conversation updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Conversation", description = "Soft-deletes an AI conversation thread.")
    public ResponseEntity<ApiResponse<Void>> deleteConversation(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        aiConversationService.deleteConversation(userId, id);
        return ResponseBuilder.ok(null, "Conversation deleted successfully");
    }

    @PostMapping("/messages")
    @Operation(summary = "Send Message to AI", description = "Sends a message to the AI assistant and receives an intelligent response.")
    public ResponseEntity<ApiResponse<AiMessageResponse>> sendMessage(@Valid @RequestBody AiMessageRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiConversationService.sendMessage(userId, request), "AI response generated");
    }

    @GetMapping("/{id}/messages")
    @Operation(summary = "Get Conversation Messages", description = "Retrieves message history for an AI conversation.")
    public ResponseEntity<ApiResponse<List<AiMessageResponse>>> getConversationMessages(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiConversationService.getConversationMessages(userId, id));
    }
}
