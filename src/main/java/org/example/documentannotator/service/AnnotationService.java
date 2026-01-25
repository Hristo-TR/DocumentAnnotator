package org.example.documentannotator.service;

import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.data.enumeration.AnnotationType;

import java.util.List;

public interface AnnotationService extends BaseService<Annotation> {
    List<Annotation> findByDocumentId(Long documentId);

    List<Annotation> findByLabelId(Long labelId);

    List<Annotation> findByType(AnnotationType type);

    List<Annotation> findByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);
}
