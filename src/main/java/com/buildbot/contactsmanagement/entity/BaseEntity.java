package com.buildbot.contactsmanagement.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;

/**
 * This class is a base class for other entity classes in the application.
 * It provides common fields for tracking creation and update timestamps.
 *
 * @MappedSuperclass indicates that this class is a mapped superclass and its fields will be inherited by its subclasses.
 * @EntityListeners(AuditingEntityListener.class) enables automatic population of created and updated timestamps using Spring Data JPA auditing.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * The date and time the entity was created.
     *
     * @CreatedDate is a Spring Data JPA annotation that automatically populates this field on entity creation.
     * @Column(updatable = false) ensures this field is not updated during entity updates.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdOn;

    /**
     * The date and time the entity was last updated.
     *
     * @LastModifiedDate is a Spring Data JPA annotation that automatically populates this field on entity updates.
     * @Column(insertable = false) ensures this field is not updated during entity creation.
     */
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDate updatedOn;
}