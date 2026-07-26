package com.lifeos.domain.ai.companion.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.ai.companion.dto.*;
import com.lifeos.domain.ai.companion.service.ConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * REST Controller for AI Companion Conversational Platform.
 */
@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
@Tag(name = "AI Companion Conversational Platform", description = "Endpoints for AI chat, real-time streaming, conversation lifecycle, feedback, and suggested quick actions")
public class AiCompanionController {

    private final ConversationService conversationService;

    @Data
    public static class CreateConversationRequest {
        private String title;
    }

    @Data
    public static class RenameRequest {
        private String title;
    }

    @PostMapping("/chat")
    @Operation(summary = "Send Chat Message", description = "Submits a user message to the AI Companion orchestration pipeline and returns response.")
    public ResponseEntity<ApiResponse<ChatMessageResponseDTO>> processChatMessage(@RequestBody ChatMessageRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(conversationService.processChatMessage(userId, request));
    }

    @PostMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "Stream Chat Message", description = "Streams AI Companion text response chunks using Server-Sent Events (SSE).")
    public SseEmitter streamChatMessage(@RequestBody ChatMessageRequestDTO request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        SseEmitter emitter = new SseEmitter(60000L);

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                ChatMessageResponseDTO response = conversationService.processChatMessage(userId, request);
                String content = response.getContent();
                String[] words = content.split(" ");

                for (String word : words) {
                    emitter.send(SseEmitter.event().data(word + " "));
                    Thread.sleep(50);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    @PostMapping("/conversation")
    @Operation(summary = "Create Conversation Session", description = "Creates a new conversation session.")
    public ResponseEntity<ApiResponse<ConversationSummaryDTO>> createConversation(@RequestBody CreateConversationRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(conversationService.createConversation(userId, request.getTitle()), "Conversation session created");
    }

    @GetMapping("/conversations")
    @Operation(summary = "List User Conversations", description = "Retrieves all conversation sessions for the active user.")
    public ResponseEntity<ApiResponse<List<ConversationSummaryDTO>>> getUserConversations() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(conversationService.getUserConversations(userId));
    }

    @GetMapping("/conversations/{id}")
    @Operation(summary = "Get Conversation Session", description = "Retrieves conversation details by ID.")
    public ResponseEntity<ApiResponse<ConversationSummaryDTO>> getConversationById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(conversationService.getConversationById(userId, id));
    }

    @DeleteMapping("/conversations/{id}")
    @Operation(summary = "Delete Conversation Session", description = "Deletes a conversation session.")
    public ResponseEntity<ApiResponse<Void>> deleteConversation(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        conversationService.deleteConversation(userId, id);
        return ResponseBuilder.ok(null, "Conversation deleted successfully");
    }

    @PostMapping("/conversations/{id}/rename")
    @Operation(summary = "Rename Conversation Session", description = "Renames a conversation title.")
    public ResponseEntity<ApiResponse<ConversationSummaryDTO>> renameConversation(@PathVariable UUID id, @RequestBody RenameRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(conversationService.renameConversation(userId, id, request.getTitle()));
    }

    @PostMapping("/conversations/{id}/archive")
    @Operation(summary = "Archive Conversation Session", description = "Archives a conversation session.")
    public ResponseEntity<ApiResponse<ConversationSummaryDTO>> archiveConversation(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(conversationService.archiveConversation(userId, id));
    }

    @PostMapping("/conversations/{id}/favorite")
    @Operation(summary = "Favorite Conversation Session", description = "Toggles favorite status for a conversation session.")
    public ResponseEntity<ApiResponse<ConversationSummaryDTO>> favoriteConversation(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(conversationService.favoriteConversation(userId, id));
    }

    @PostMapping("/conversations/{id}/feedback")
    @Operation(summary = "Record Message Feedback", description = "Records user feedback (Thumbs up, thumbs down, hallucination report).")
    public ResponseEntity<ApiResponse<Void>> recordFeedback(@PathVariable UUID id, @RequestBody FeedbackRequestDTO feedback) {
        UUID userId = SecurityUtils.getCurrentUserId();
        conversationService.recordFeedback(userId, id, feedback);
        return ResponseBuilder.ok(null, "Feedback recorded successfully");
    }

    @PostMapping("/conversations/{id}/summary")
    @Operation(summary = "Generate Conversation Summary", description = "Generates a rolling executive summary for a conversation session.")
    public ResponseEntity<ApiResponse<String>> generateSummary(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(conversationService.generateConversationSummary(userId, id));
    }
}
