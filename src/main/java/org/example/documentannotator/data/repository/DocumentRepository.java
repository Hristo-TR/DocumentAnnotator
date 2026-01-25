package org.example.documentannotator.data.repository;

import org.example.documentannotator.data.entity.Document;
import org.example.documentannotator.data.enumeration.FileType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByName(String name);

    List<Document> findByFileType(FileType fileType);

    List<Document> findByNameContainingIgnoreCase(String name);
}
