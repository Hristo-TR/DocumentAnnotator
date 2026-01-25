package org.example.documentannotator.converter;

import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;

public abstract class BaseConverter<E, D> {

    public D toDto(E entity) {
        if (entity == null) {
            return null;
        }
        D dto = createDto();
        BeanUtils.copyProperties(entity, dto, ignoredToDto());
        return dto;
    }

    public List<D> toDtoList(Collection<E> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toDto).toList();
    }

    public E toEntity(D dto) {
        if (dto == null) {
            return null;
        }
        E entity = createEntity();
        BeanUtils.copyProperties(dto, entity, ignoredToEntity());
        return entity;
    }

    protected String[] ignoredToDto() {
        return new String[0];
    }

    protected String[] ignoredToEntity() {
        return new String[0];
    }

    protected abstract E createEntity();

    protected abstract D createDto();
}
