package com.lifeos.module.people.repository;

import com.lifeos.module.people.entity.ContactEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository for people.contacts table.
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, UUID> {

    Page<ContactEntity> findByUserIdAndIsDeletedFalse(UUID userId, Pageable pageable);

    Optional<ContactEntity> findByIdAndUserIdAndIsDeletedFalse(UUID id, UUID userId);

    Page<ContactEntity> findByUserIdAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndIsDeletedFalse(
            UUID userId, String firstName, String lastName, Pageable pageable
    );
}
