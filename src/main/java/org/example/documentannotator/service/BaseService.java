package org.example.documentannotator.service;

import org.example.documentannotator.data.entity.BaseEntity;
import org.example.documentannotator.data.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseService<E extends BaseEntity> {
    default E save(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        return getRepository().save(entity);
    }

    default void delete(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        getLogger().info("Deleting entity: {}", entity);
        getRepository().delete(entity);
    }

    default void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        getLogger().info("Deleting entity with id: {}", id);
        getRepository().deleteById(id);
    }

    default E findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return getRepository().findById(id).orElseThrow(EntityNotFoundException::new);
    }

    JpaRepository<E, Long> getRepository();

    Logger getLogger();
}
