package org.example.documentannotator.service;

import org.example.documentannotator.data.entity.BaseEntity;
import org.example.documentannotator.data.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Base service class, providing the essential CRUD operations and functionalities for all entities
 *
 * @param <E> the entity class, the service provides a functionality for
 */
public interface BaseService<E extends BaseEntity> {
    /**
     * Base save entity logic
     */
    default E save(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        return getRepository().save(entity);
    }

    /**
     * Base delete entity logic
     */
    default void delete(E entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity cannot be null");
        }
        getLogger().info("Deleting entity: {}", entity);
        getRepository().delete(entity);
    }

    /**
     * Base delete entity by given id logic
     */
    default void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        getLogger().info("Deleting entity with id: {}", id);
        getRepository().deleteById(id);
    }

    /**
     * Base find entity by given id logic
     */
    default E findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return getRepository().findById(id).orElseThrow(EntityNotFoundException::new);
    }

    // Utility methods, providing the default methods with access to subclass objects

    JpaRepository<E, Long> getRepository();

    Logger getLogger();
}
