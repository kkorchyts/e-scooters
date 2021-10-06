package com.kkorchyts.dto.converters;

import com.kkorchyts.dto.exceptions.DtoException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Slice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseConverter<E, D> {
    E createEntityFromDto(D dto);

    D createDtoFromEntity(E entity);

    E updateEntity(E entity, D dto);

    default List<D> createFromEntities(final Collection<E> entities) {
        if (entities == null) {
            throw new DtoException("Collection of entity is null!");
        } else {
            return entities.stream()
                    .map(this::createDtoFromEntity)
                    .collect(Collectors.toList());
        }
    }

    default Page<D> createFromEntitiesPage(final Slice<E> entities) {
        if (entities == null) {
            throw new DtoException("Collection of entity is null!");
        } else {
            List<D> result = createFromEntities(entities.getContent());
            return new PageImpl<>(result, entities.getPageable(), entities.getNumberOfElements());
        }
    }
}
