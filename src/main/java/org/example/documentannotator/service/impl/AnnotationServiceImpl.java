package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.example.documentannotator.service.AnnotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnotationServiceImpl implements AnnotationService {

    private final Logger logger = LoggerFactory.getLogger(AnnotationServiceImpl.class);
    private final AnnotationRepository repository;

    public AnnotationServiceImpl(AnnotationRepository annotationRepository) {
        this.repository = annotationRepository;
    }

    @Override
    public JpaRepository<Annotation, Long> getRepository() {
        return repository;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public List<Annotation> findByDocumentId(Long documentId) {
        return repository.findByDocumentId(documentId);
    }

    @Override
    public List<Annotation> findByLabelId(Long labelId) {
        return repository.findByLabelId(labelId);
    }

    @Override
    public List<Annotation> findByType(org.example.documentannotator.data.enumeration.AnnotationType type) {
        return repository.findByAnnotationType(type);
    }

    @Override
    public List<Annotation> findByDocumentIdAndPageNumber(Long documentId, Integer pageNumber) {
        return repository.findByDocumentIdAndPageNumber(documentId, pageNumber);
    }
}
