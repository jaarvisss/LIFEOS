package com.lifeos.module.ai.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.ai.dto.AiSuggestionResponse;
import com.lifeos.module.ai.service.AiSuggestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for AI Proactive Suggestions & Recommendations.
 */
@RestController
@RequestMapping("/api/v1/ai/suggestions")
@RequiredArgsConstructor
@Tag(name = "AI Proactive Suggestions", description = "Endpoints for retrieving AI-generated productivity, health, and goal recommendations")
public class AiSuggestionController {

    private final AiSuggestionService aiSuggestionService;

    @GetMapping
    @Operation(summary = "Get Active Suggestions", description = "Retrieves active AI recommendations ordered by confidence score.")
    public ResponseEntity<ApiResponse<List<AiSuggestionResponse>>> getActiveSuggestions() {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiSuggestionService.getActiveSuggestions(userId));
    }

    @PostMapping("/{id}/respond")
    @Operation(summary = "Respond to Suggestion", description = "Accepts or dismisses an AI recommendation.")
    public ResponseEntity<ApiResponse<AiSuggestionResponse>> respondToSuggestion(
            @PathVariable UUID id,
            @RequestParam boolean accept) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(aiSuggestionService.respondToSuggestion(userId, id, accept), "Suggestion updated");
    }
}
