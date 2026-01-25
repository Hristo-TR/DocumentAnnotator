package org.example.documentannotator.controller;

import jakarta.validation.Valid;
import org.example.documentannotator.converter.AnnotationConverter;
import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.data.entity.Document;
import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.data.enumeration.AnnotationType;
import org.example.documentannotator.dto.AnnotationDto;
import org.example.documentannotator.service.AnnotationService;
import org.example.documentannotator.service.DocumentService;
import org.example.documentannotator.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/annotations")
public class AnnotationController {
    private final AnnotationService annotationService;
    private final DocumentService documentService;
    private final LabelService labelService;
    private final AnnotationConverter annotationConverter;

    public AnnotationController(AnnotationService annotationService,
                                DocumentService documentService,
                                LabelService labelService,
                                AnnotationConverter annotationConverter) {
        this.annotationService = annotationService;
        this.documentService = documentService;
        this.labelService = labelService;
        this.annotationConverter = annotationConverter;
    }

    @PostMapping
    public ResponseEntity<AnnotationDto> createAnnotation(@Valid @RequestBody AnnotationDto annotationDto) {
        Annotation annotation = annotationConverter.toEntity(annotationDto);

        Document document = documentService.findById(annotationDto.getDocumentId());
        Label label = labelService.findById(annotationDto.getLabelId());

        annotation.setDocument(document);
        annotation.setLabel(label);

        Annotation saved = annotationService.save(annotation);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(annotationConverter.toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<AnnotationDto>> getAllAnnotations(
            @RequestParam(required = false) Long documentId,
            @RequestParam(required = false) Long labelId,
            @RequestParam(required = false) AnnotationType type,
            @RequestParam(required = false) Integer pageNumber) {
        List<Annotation> annotations;

        if (documentId != null && pageNumber != null) {
            annotations = annotationService.findByDocumentIdAndPageNumber(documentId, pageNumber);
        } else if (documentId != null) {
            annotations = annotationService.findByDocumentId(documentId);
        } else if (labelId != null) {
            annotations = annotationService.findByLabelId(labelId);
        } else if (type != null) {
            annotations = annotationService.findByType(type);
        } else {
            annotations = annotationService.getRepository().findAll();
        }

        return ResponseEntity.ok(annotationConverter.toDtoList(annotations));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnnotationDto> getAnnotation(@PathVariable Long id) {
        Annotation annotation = annotationService.findById(id);
        return ResponseEntity.ok(annotationConverter.toDto(annotation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnnotationDto> updateAnnotation(@PathVariable Long id, @Valid @RequestBody AnnotationDto annotationDto) {
        Annotation existing = annotationService.findById(id);
        Annotation updated = annotationConverter.toEntity(annotationDto);

        updated.setId(id);

        Document document = documentService.findById(annotationDto.getDocumentId());
        Label label = labelService.findById(annotationDto.getLabelId());

        updated.setDocument(document);
        updated.setLabel(label);

        Annotation saved = annotationService.save(updated);
        return ResponseEntity.ok(annotationConverter.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnotation(@PathVariable Long id) {
        annotationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
