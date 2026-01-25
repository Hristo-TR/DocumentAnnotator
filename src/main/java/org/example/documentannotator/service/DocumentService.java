package org.example.documentannotator.service;

import org.example.documentannotator.data.entity.Document;
import org.example.documentannotator.data.enumeration.FileType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentService extends BaseService<Document> {
    Document uploadDocument(MultipartFile file) throws Exception;

    Document createTextDocument(String name, String text) throws Exception;

    String getDocumentContent(Long documentId);

    byte[] downloadDocument(Long documentId);

    List<Document> findAll(String q, FileType type);

    Optional<Document> findByName(String name);
}
