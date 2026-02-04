package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.data.exception.EntityInUseException;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.example.documentannotator.data.repository.LabelLinkRepository;
import org.example.documentannotator.data.repository.LabelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class LabelServiceImplTest {

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private AnnotationRepository annotationRepository;

    @Mock
    private LabelLinkRepository labelLinkRepository;

    @InjectMocks
    private LabelServiceImpl labelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        labelService = new LabelServiceImpl(labelRepository, annotationRepository, labelLinkRepository);
    }

    @Test
    void findAllParents_returnsOnlyRootLabels() {
        Label root = new Label("Root", "#ff0000", null, Instant.now(), Instant.now());
        root.setId(1L);

        Label child = new Label("Child", "#00ff00", root, Instant.now(), Instant.now());
        child.setId(2L);

        when(labelRepository.findAll()).thenReturn(List.of(root, child));

        List<Label> parents = labelService.findAllParents();

        assertEquals(1, parents.size());
        assertEquals(root.getId(), parents.getFirst().getId());
    }

    @Test
    void validateParent_sameId_throws() {
        Long id = 1L;
        assertThrows(IllegalArgumentException.class, () -> labelService.validateParent(id, id));
    }

    @Test
    void deleteById_inUseInAnnotations_throws() {
        Long id = 1L;
        when(annotationRepository.existsByLabelId(id)).thenReturn(true);

        assertThrows(EntityInUseException.class, () -> labelService.deleteById(id));
        verify(labelRepository, never()).deleteById(any());
    }
}

