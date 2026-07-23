package com.lifeos.module.people.controller;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.ApiResponse;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.common.response.ResponseBuilder;
import com.lifeos.common.util.SecurityUtils;
import com.lifeos.module.people.dto.ContactRequest;
import com.lifeos.module.people.dto.ContactResponse;
import com.lifeos.module.people.service.ContactService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST Controller for People & Personal CRM Contacts Management.
 */
@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor
@Tag(name = "People & Contacts", description = "Endpoints for managing personal contacts, CRM profiles, and search")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    @Operation(summary = "Create Contact", description = "Creates a new contact profile.")
    public ResponseEntity<ApiResponse<ContactResponse>> createContact(@Valid @RequestBody ContactRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.created(contactService.createContact(userId, request), "Contact created successfully");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Contact by ID", description = "Retrieves contact profile by identifier.")
    public ResponseEntity<ApiResponse<ContactResponse>> getContactById(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(contactService.getContactById(userId, id));
    }

    @GetMapping
    @Operation(summary = "List Contacts", description = "Retrieves paginated list of contacts.")
    public ResponseEntity<ApiResponse<PagedResponse<ContactResponse>>> getAllContacts(@Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(contactService.getAllContacts(userId, paginationRequest));
    }

    @GetMapping("/search")
    @Operation(summary = "Search Contacts", description = "Searches contacts by first or last name.")
    public ResponseEntity<ApiResponse<PagedResponse<ContactResponse>>> searchContacts(
            @RequestParam("q") String query,
            @Valid PaginationRequest paginationRequest) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(contactService.searchContacts(userId, query, paginationRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Contact", description = "Updates contact information.")
    public ResponseEntity<ApiResponse<ContactResponse>> updateContact(
            @PathVariable UUID id,
            @Valid @RequestBody ContactRequest request) {
        UUID userId = SecurityUtils.getCurrentUserId();
        return ResponseBuilder.ok(contactService.updateContact(userId, id, request), "Contact updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Contact", description = "Soft-deletes a contact record.")
    public ResponseEntity<ApiResponse<Void>> deleteContact(@PathVariable UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();
        contactService.deleteContact(userId, id);
        return ResponseBuilder.ok(null, "Contact deleted successfully");
    }
}
