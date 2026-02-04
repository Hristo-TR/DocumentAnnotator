package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.data.enumeration.AnnotationType;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AnnotationServiceImplTest {

    @Mock
    private AnnotationRepository annotationRepository;

    @InjectMocks
    private AnnotationServiceImpl annotationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        annotationService = new AnnotationServiceImpl(annotationRepository);
    }

    @Test
    void findByDocumentId_delegatesToRepository() {
        Long docId = 5L;
        when(annotationRepository.findByDocumentId(docId)).thenReturn(List.of(new Annotation()));

        List<Annotation> result = annotationService.findByDocumentId(docId);

        assertEquals(1, result.size());
        verify(annotationRepository).findByDocumentId(docId);
    }

    @Test
    void findByType_delegatesToRepository() {
        when(annotationRepository.findByAnnotationType(AnnotationType.TEXT))
                .thenReturn(List.of(new Annotation()));

        List<Annotation> result = annotationService.findByType(AnnotationType.TEXT);

        assertEquals(1, result.size());
        verify(annotationRepository).findByAnnotationType(AnnotationType.TEXT);
    }
}

