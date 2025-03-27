package br.com.trapp.cadastroagendabackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "timestamp")
    protected LocalDateTime createdAt;

    @Version
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamp")
    protected LocalDateTime updatedAt;

}
