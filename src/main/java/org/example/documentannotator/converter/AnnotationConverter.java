package org.example.documentannotator.converter;

import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.dto.AnnotationDto;
import org.springframework.stereotype.Component;

@Component
public class AnnotationConverter extends BaseConverter<Annotation, AnnotationDto> {
    @Override
    protected String[] ignoredToEntity() {
        return new String[]{"id", "document", "label"};
    }

    @Override
    public AnnotationDto toDto(Annotation entity) {
        AnnotationDto dto = super.toDto(entity);
        if (dto == null) {
            return null;
        }
        dto.setDocumentId(entity.getDocument() != null ? entity.getDocument().getId() : null);
        dto.setLabelId(entity.getLabel() != null ? entity.getLabel().getId() : null);
        return dto;
    }

    @Override
    protected Annotation createEntity() {
        return new Annotation();
    }

    @Override
    protected AnnotationDto createDto() {
        return new AnnotationDto();
    }
}
