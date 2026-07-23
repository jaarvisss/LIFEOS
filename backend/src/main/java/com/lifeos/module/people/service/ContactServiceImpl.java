package com.lifeos.module.people.service;

import com.lifeos.common.exception.ResourceNotFoundException;
import com.lifeos.common.request.PaginationRequest;
import com.lifeos.common.response.PagedResponse;
import com.lifeos.module.people.dto.ContactRequest;
import com.lifeos.module.people.dto.ContactResponse;
import com.lifeos.module.people.entity.ContactEntity;
import com.lifeos.module.people.mapper.ContactMapper;
import com.lifeos.module.people.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of ContactService interface.
 */
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Override
    @Transactional
    public ContactResponse createContact(UUID userId, ContactRequest request) {
        ContactEntity contact = contactMapper.toEntity(request);
        contact.setUserId(userId);
        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public ContactResponse getContactById(UUID userId, UUID id) {
        ContactEntity contact = contactRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "id", id));
        return contactMapper.toDto(contact);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ContactResponse> getAllContacts(UUID userId, PaginationRequest paginationRequest) {
        Page<ContactEntity> page = contactRepository.findByUserIdAndIsDeletedFalse(userId, paginationRequest.toPageable());
        return PagedResponse.from(page.map(contactMapper::toDto));
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<ContactResponse> searchContacts(UUID userId, String query, PaginationRequest paginationRequest) {
        Page<ContactEntity> page = contactRepository.findByUserIdAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndIsDeletedFalse(
                userId, query, query, paginationRequest.toPageable()
        );
        return PagedResponse.from(page.map(contactMapper::toDto));
    }

    @Override
    @Transactional
    public ContactResponse updateContact(UUID userId, UUID id, ContactRequest request) {
        ContactEntity contact = contactRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "id", id));

        contactMapper.updateEntityFromDto(request, contact);
        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }

    @Override
    @Transactional
    public void deleteContact(UUID userId, UUID id) {
        ContactEntity contact = contactRepository.findByIdAndUserIdAndIsDeletedFalse(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact", "id", id));
        contact.markDeleted();
        contactRepository.save(contact);
    }
}
