package org.example.documentannotator.converter;

import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;

/**
 * Base converter between entities and DTOs
 *
 * @param <E> Entity class
 * @param <D> DTO class
 */
public abstract class BaseConverter<E, D> {

    /**
     * Base method for converting an entity to a dto
     *
     * @param entity entity object
     * @return dto converted from entity
     */
    public D toDto(E entity) {
        if (entity == null) {
            return null;
        }
        D dto = createDto();
        BeanUtils.copyProperties(entity, dto, ignoredToDto());
        return dto;
    }

    /**
     * Base method for converting a batch of entities to DTOs
     *
     * @param entities
     * @return
     */
    public List<D> toDtoList(Collection<E> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toDto).toList();
    }

    /**
     * Base method for converting DTOs to entites
     *
     * @param dto DTO object
     * @return entity converted from DTO
     */
    public E toEntity(D dto) {
        if (dto == null) {
            return null;
        }
        E entity = createEntity();
        BeanUtils.copyProperties(dto, entity, ignoredToEntity());
        return entity;
    }

    /**
     * Used for specifying which fields of an entity to be skipped during conversion
     */
    protected String[] ignoredToDto() {
        return new String[0];
    }

    /**
     * Used for specifying which fields of a DTO to be skipped during conversion
     */
    protected String[] ignoredToEntity() {
        return new String[0];
    }

    protected abstract E createEntity();

    protected abstract D createDto();
}
