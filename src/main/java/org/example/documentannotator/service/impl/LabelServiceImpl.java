package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.data.repository.LabelRepository;
import org.example.documentannotator.service.LabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl implements LabelService {

    private final Logger logger = LoggerFactory.getLogger(LabelServiceImpl.class);
    private final LabelRepository repository;

    public LabelServiceImpl(LabelRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<Label, Long> getRepository() {
        return repository;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
