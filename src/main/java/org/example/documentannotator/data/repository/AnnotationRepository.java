package org.example.documentannotator.data.repository;

import org.example.documentannotator.data.entity.Annotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnotationRepository extends JpaRepository<Annotation, Long> {
}
