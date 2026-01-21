package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.LabelLink;
import org.example.documentannotator.data.repository.LabelLinkRepository;
import org.example.documentannotator.service.LabelLinkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelLinkServiceImpl implements LabelLinkService {

    private final Logger logger = LoggerFactory.getLogger(LabelLinkServiceImpl.class);
    private final LabelLinkRepository repository;

    public LabelLinkServiceImpl(LabelLinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public JpaRepository<LabelLink, Long> getRepository() {
        return repository;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
