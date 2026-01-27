package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Document;
import org.example.documentannotator.data.enumeration.FileType;
import org.example.documentannotator.data.exception.DuplicateDocumentException;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.example.documentannotator.data.repository.DocumentRepository;
import org.example.documentannotator.service.DocumentService;
import org.example.documentannotator.util.DocxHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private final DocumentRepository repository;
    private final AnnotationRepository annotationRepository;
    private final String storagePath;

    public DocumentServiceImpl(DocumentRepository repository,
                               AnnotationRepository annotationRepository,
                               @Value("${app.storage.path}") String storagePath) {
        this.repository = repository;
        this.annotationRepository = annotationRepository;
        this.storagePath = storagePath;
        try {
            Files.createDirectories(Paths.get(storagePath));
        } catch (IOException e) {
            logger.error("Failed to create storage directory", e);
        }
    }

    @Override
    public JpaRepository<Document, Long> getRepository() {
        return repository;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public Document uploadDocument(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be empty");
        }

        if (repository.findByName(originalFilename).isPresent()) {
            throw new DuplicateDocumentException("Document with name '" + originalFilename + "' already exists");
        }

        FileType fileType = determineFileType(originalFilename);
        Path filePath = Paths.get(storagePath, originalFilename);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Document document = new Document();
        document.setName(originalFilename);
        document.setPath(filePath.toString());
        document.setFileType(fileType);

        return repository.save(document);
    }

    @Override
    public Document createTextDocument(String name, String text) throws Exception {
        if (repository.findByName(name).isPresent()) {
            throw new DuplicateDocumentException("Document with name '" + name + "' already exists");
        }

        Path filePath = Paths.get(storagePath, name);
        Files.writeString(filePath, text);

        Document document = new Document();
        document.setName(name);
        document.setPath(filePath.toString());
        document.setFileType(FileType.TXT);

        return repository.save(document);
    }

    @Override
    public String getDocumentContent(Long documentId) {
        Document document = findById(documentId);
        if (document.getFileType() != FileType.TXT) {
            throw new IllegalArgumentException("Content extraction only supported for TXT files. Use /download endpoint for other file types.");
        }
        try {
            return Files.readString(Paths.get(document.getPath()));
        } catch (IOException e) {
            logger.error("Failed to read document content", e);
            throw new RuntimeException("Failed to read document content", e);
        }
    }

    @Override
    public String getDocumentAsHtml(Long documentId) {
        Document document = findById(documentId);
        if (document.getFileType() != FileType.DOCX) {
            throw new IllegalArgumentException("HTML conversion only supported for DOCX files.");
        }
        try {
            return DocxHelper.convertToHtml(document.getPath());
        } catch (IOException e) {
            logger.error("Failed to convert DOCX to HTML", e);
            throw new RuntimeException("Failed to convert DOCX to HTML", e);
        }
    }

    @Override
    public byte[] downloadDocument(Long documentId) {
        Document document = findById(documentId);
        try {
            return Files.readAllBytes(Paths.get(document.getPath()));
        } catch (IOException e) {
            logger.error("Failed to read document file", e);
            throw new RuntimeException("Failed to read document file", e);
        }
    }

    @Override
    public List<Document> findAll(String q, FileType type) {
        if (q != null && !q.isEmpty() && type != null) {
            return repository.findByNameContainingIgnoreCase(q).stream()
                    .filter(doc -> doc.getFileType() == type)
                    .toList();
        } else if (q != null && !q.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(q);
        } else if (type != null) {
            return repository.findByFileType(type);
        } else {
            return repository.findAll();
        }
    }

    @Override
    public Optional<Document> findByName(String name) {
        return repository.findByName(name);
    }

    private FileType determineFileType(String filename) {
        String lower = filename.toLowerCase();
        if (lower.endsWith(".txt")) {
            return FileType.TXT;
        } else if (lower.endsWith(".pdf")) {
            return FileType.PDF;
        } else if (lower.endsWith(".docx")) {
            return FileType.DOCX;
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + filename);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Document document = findById(id);
        annotationRepository.deleteByDocumentId(id);
        repository.delete(document);
        try {
            Files.deleteIfExists(Paths.get(document.getPath()));
        } catch (IOException e) {
            logger.error("Failed to delete document file", e);
        }
    }
}
