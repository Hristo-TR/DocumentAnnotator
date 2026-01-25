package org.example.documentannotator.converter;

import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.dto.LabelDto;
import org.springframework.stereotype.Component;

@Component
public class LabelConverter extends BaseConverter<Label, LabelDto> {
    @Override
    protected String[] ignoredToEntity() {
        return new String[]{"id", "createdAt", "updatedAt", "parentLabel"};
    }

    @Override
    public LabelDto toDto(Label entity) {
        LabelDto dto = super.toDto(entity);
        if (dto == null) {
            return null;
        }
        dto.setParentLabelId(entity.getParentLabel() != null ? entity.getParentLabel().getId() : null);
        return dto;
    }

    @Override
    protected Label createEntity() {
        return new Label();
    }

    @Override
    protected LabelDto createDto() {
        return new LabelDto();
    }
}
