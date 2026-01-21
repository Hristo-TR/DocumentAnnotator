package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Document;
import org.example.documentannotator.data.repository.DocumentRepository;
import org.example.documentannotator.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);
    private final DocumentRepository repository;

    public DocumentServiceImpl(DocumentRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<Document, Long> getRepository() {
        return repository;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
