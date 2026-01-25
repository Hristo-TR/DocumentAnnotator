package org.example.documentannotator.data.repository;

import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.data.entity.Document;
import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.data.enumeration.AnnotationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
    List<Annotation> findByDocument(Document document);

    List<Annotation> findByDocumentId(Long documentId);

    List<Annotation> findByLabel(Label label);

    List<Annotation> findByLabelId(Long labelId);

    List<Annotation> findByAnnotationType(AnnotationType annotationType);

    List<Annotation> findByDocumentIdAndPageNumber(Long documentId, Integer pageNumber);

    List<Annotation> findByDocumentIdAndLabelId(Long documentId, Long labelId);

    @Query("SELECT a.label.id, a.label.name, COUNT(a) as cnt FROM Annotation a WHERE a.document.id = :documentId GROUP BY a.label.id, a.label.name ORDER BY cnt DESC")
    List<Object[]> findTopLabelsByDocumentId(Long documentId);

    @Query("SELECT a.label.id, a.label.name, COUNT(a) as cnt FROM Annotation a GROUP BY a.label.id, a.label.name ORDER BY cnt DESC")
    List<Object[]> findTopLabelsGlobal();

    boolean existsByDocumentId(Long documentId);

    boolean existsByLabelId(Long labelId);

    void deleteByDocumentId(Long documentId);
}
