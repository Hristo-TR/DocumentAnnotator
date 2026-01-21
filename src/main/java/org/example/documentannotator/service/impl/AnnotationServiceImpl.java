package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.example.documentannotator.service.AnnotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
}
