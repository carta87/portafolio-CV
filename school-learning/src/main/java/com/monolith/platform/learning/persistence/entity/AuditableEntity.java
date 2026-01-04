package com.monolith.platform.learning.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {

    @Column(name = "fecha creacion")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "fecha modificacion")
    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
