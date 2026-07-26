package com.lifeos.domain.memory.intelligence.controller;

import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.domain.memory.intelligence.dto.*;
import com.lifeos.domain.memory.intelligence.service.MemoryIntelligenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST Controller for AI Memory Intelligence Engine endpoints.
 */
@RestController
@RequestMapping("/api/v1/memory")
@RequiredArgsConstructor
@Tag(name = "Memory AI Intelligence Engine", description = "AI-driven extraction, classification, summarization, entity extraction, and duplicate detection powered by Google Gemini")
public class MemoryIntelligenceController {

    private final MemoryIntelligenceService intelligenceService;

    @Data
    public static class MemoryInputRequest {
        private String text;
    }

    @PostMapping("/extract")
    @Operation(summary = "Extract Memory", description = "Extracts structured memory attributes from raw text.")
    public ResponseEntity<ApiResponse<ExtractedMemoryDTO>> extractMemory(@RequestBody MemoryInputRequest request) {
        return ResponseBuilder.ok(intelligenceService.extractMemory(request.getText()));
    }

    @PostMapping("/classify")
    @Operation(summary = "Classify Memory", description = "Classifies raw text into one of 17 memory types with confidence scores.")
    public ResponseEntity<ApiResponse<Map<String, Object>>> classifyMemory(@RequestBody MemoryInputRequest request) {
        return ResponseBuilder.ok(intelligenceService.classifyMemory(request.getText()));
    }

    @PostMapping("/summarize")
    @Operation(summary = "Summarize Memory", description = "Generates short, medium, and executive summaries.")
    public ResponseEntity<ApiResponse<Map<String, Object>>> summarizeMemory(@RequestBody MemoryInputRequest request) {
        return ResponseBuilder.ok(intelligenceService.summarizeMemory(request.getText()));
    }

    @PostMapping("/tags")
    @Operation(summary = "Generate Tags", description = "Generates 3 to 10 intelligent tags.")
    public ResponseEntity<ApiResponse<List<String>>> generateTags(@RequestBody MemoryInputRequest request) {
        return ResponseBuilder.ok(intelligenceService.generateTags(request.getText()));
    }

    @PostMapping("/entities")
    @Operation(summary = "Extract Entities", description = "Extracts named entities (Person, Org, Date, Time, Location).")
    public ResponseEntity<ApiResponse<List<ExtractedEntityDTO>>> extractEntities(@RequestBody MemoryInputRequest request) {
        return ResponseBuilder.ok(intelligenceService.extractEntities(request.getText()));
    }

    @PostMapping("/analyze")
    @Operation(summary = "Analyze Memory Pipeline", description = "Executes complete AI extraction, classification, scoring, entity extraction pipeline.")
    public ResponseEntity<ApiResponse<ExtractedMemoryDTO>> analyzeMemory(@RequestBody MemoryInputRequest request) {
        return ResponseBuilder.ok(intelligenceService.analyzeMemory(request.getText()));
    }

    @PostMapping("/duplicate-check")
    @Operation(summary = "Duplicate & Similarity Check", description = "Checks raw text against existing memory graph for duplicates or similarities.")
    public ResponseEntity<ApiResponse<DuplicateCheckResultDTO>> checkDuplicate(@RequestBody MemoryInputRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(intelligenceService.checkDuplicate(userId, request.getText()));
    }

    @PostMapping("/enrich")
    @Operation(summary = "Enrich Memory", description = "Generates related topics, suggested categories, and follow-up actions.")
    public ResponseEntity<ApiResponse<Map<String, Object>>> enrichMemory(@RequestBody MemoryInputRequest request) {
        return ResponseBuilder.ok(intelligenceService.enrichMemory(request.getText()));
    }
}
