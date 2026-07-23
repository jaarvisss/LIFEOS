package com.lifeos.module.people.service;

import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.people.dto.ContactRequest;
import com.lifeos.module.people.dto.ContactResponse;

import java.util.UUID;

/**
 * Service Interface for Contact operations.
 */
public interface ContactService {

    ContactResponse createContact(UUID userId, ContactRequest request);

    ContactResponse getContactById(UUID userId, UUID id);

    PagedResponse<ContactResponse> getAllContacts(UUID userId, PaginationRequest paginationRequest);

    PagedResponse<ContactResponse> searchContacts(UUID userId, String query, PaginationRequest paginationRequest);

    ContactResponse updateContact(UUID userId, UUID id, ContactRequest request);

    void deleteContact(UUID userId, UUID id);
}
