package com.av.pixel.dao.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    Long id;

    @Column(name = "created", nullable = false)
    Date created;

    @Column(name = "updated", nullable = false)
    Date updated;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    Boolean deleted;

    @PrePersist
    protected void onCreate() {
        Date currentDate = new Date();
        if (Objects.isNull(this.created)) {
            this.created = currentDate;
        }
        if (Objects.isNull(this.updated)) {
            this.updated = currentDate;
        }
        if (Objects.isNull(this.deleted)) {
            this.deleted = false;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = new Date();;
    }
}
