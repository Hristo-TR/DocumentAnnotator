package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.data.enumeration.AnnotationType;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.example.documentannotator.data.repository.DocumentRepository;
import org.example.documentannotator.data.repository.LabelLinkRepository;
import org.example.documentannotator.data.repository.LabelRepository;
import org.example.documentannotator.dto.LabelDensityResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ReportsServiceImplTest {

    @Mock
    private AnnotationRepository annotationRepository;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private LabelLinkRepository labelLinkRepository;

    @InjectMocks
    private ReportsServiceImpl reportsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reportsService = new ReportsServiceImpl(annotationRepository, documentRepository, labelRepository, labelLinkRepository);
    }

    @Test
    void getLabelDensity_countsRegionAnnotationsPerPage() {
        Long documentId = 1L;
        Long labelId = 2L;

        Annotation a1 = new Annotation();
        a1.setAnnotationType(AnnotationType.REGION);
        a1.setPageNumber(1);

        Annotation a2 = new Annotation();
        a2.setAnnotationType(AnnotationType.REGION);
        a2.setPageNumber(1);

        Annotation a3 = new Annotation();
        a3.setAnnotationType(AnnotationType.REGION);
        a3.setPageNumber(2);

        Annotation text = new Annotation();
        text.setAnnotationType(AnnotationType.TEXT);

        when(annotationRepository.findByDocumentIdAndLabelId(documentId, labelId))
                .thenReturn(List.of(a1, a2, a3, text));

        List<LabelDensityResponse> density = reportsService.getLabelDensity(documentId, labelId);

        assertEquals(2, density.size());
        assertEquals("page-1", density.get(0).getBucket());
        assertEquals(2L, density.get(0).getCount());
        assertEquals("page-2", density.get(1).getBucket());
        assertEquals(1L, density.get(1).getCount());
    }
}

