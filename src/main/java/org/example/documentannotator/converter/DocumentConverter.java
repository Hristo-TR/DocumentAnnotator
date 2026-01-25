package org.example.documentannotator.converter;

import org.example.documentannotator.data.entity.Document;
import org.example.documentannotator.dto.DocumentDto;
import org.springframework.stereotype.Component;

@Component
public class DocumentConverter extends BaseConverter<Document, DocumentDto> {
    @Override
    protected String[] ignoredToEntity() {
        return new String[]{"id", "createdAt", "updatedAt"};
    }

    @Override
    protected Document createEntity() {
        return new Document();
    }

    @Override
    protected DocumentDto createDto() {
        return new DocumentDto();
    }
}
