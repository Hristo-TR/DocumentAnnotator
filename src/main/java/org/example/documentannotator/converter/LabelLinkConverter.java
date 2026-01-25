package org.example.documentannotator.converter;

import org.example.documentannotator.data.entity.LabelLink;
import org.example.documentannotator.dto.LabelLinkDto;
import org.springframework.stereotype.Component;

@Component
public class LabelLinkConverter extends BaseConverter<LabelLink, LabelLinkDto> {
    @Override
    protected String[] ignoredToEntity() {
        return new String[]{"id", "fromLabel", "toLabel"};
    }

    @Override
    public LabelLinkDto toDto(LabelLink entity) {
        LabelLinkDto dto = super.toDto(entity);
        if (dto == null) {
            return null;
        }
        dto.setFromLabelId(entity.getFromLabel() != null ? entity.getFromLabel().getId() : null);
        dto.setToLabelId(entity.getToLabel() != null ? entity.getToLabel().getId() : null);
        return dto;
    }

    @Override
    protected LabelLink createEntity() {
        return new LabelLink();
    }

    @Override
    protected LabelLinkDto createDto() {
        return new LabelLinkDto();
    }
}
