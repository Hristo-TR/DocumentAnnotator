package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.data.exception.EntityInUseException;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.example.documentannotator.data.repository.LabelLinkRepository;
import org.example.documentannotator.data.repository.LabelRepository;
import org.example.documentannotator.service.LabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {

    private final Logger logger = LoggerFactory.getLogger(LabelServiceImpl.class);
    private final LabelRepository repository;
    private final AnnotationRepository annotationRepository;
    private final LabelLinkRepository labelLinkRepository;

    public LabelServiceImpl(LabelRepository repository,
                            AnnotationRepository annotationRepository,
                            LabelLinkRepository labelLinkRepository) {
        this.repository = repository;
        this.annotationRepository = annotationRepository;
        this.labelLinkRepository = labelLinkRepository;
    }

    @Override
    public JpaRepository<Label, Long> getRepository() {
        return repository;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public List<Label> findAllAsTree() {
        List<Label> allLabels = repository.findAll();
        return allLabels.stream()
                .filter(label -> label.getParentLabel() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Label> findAllFlat() {
        return repository.findAll();
    }

    @Override
    public void validateParent(Long labelId, Long parentLabelId) {
        if (parentLabelId == null) {
            return;
        }
        if (labelId != null && labelId.equals(parentLabelId)) {
            throw new IllegalArgumentException("Label cannot be its own parent");
        }
        if (labelId != null && isDescendant(parentLabelId, labelId)) {
            throw new IllegalArgumentException("Cannot set parent: would create circular reference");
        }
    }

    private boolean isDescendant(Long potentialDescendantId, Long ancestorId) {
        Label current = repository.findById(potentialDescendantId).orElse(null);
        while (current != null && current.getParentLabel() != null) {
            if (current.getParentLabel().getId().equals(ancestorId)) {
                return true;
            }
            current = current.getParentLabel();
        }
        return false;
    }

    @Override
    public void deleteById(Long id) {
        if (annotationRepository.existsByLabelId(id)) {
            throw new EntityInUseException("Cannot delete label: it is used in annotations");
        }
        if (labelLinkRepository.existsByFromLabelId(id) || labelLinkRepository.existsByToLabelId(id)) {
            throw new EntityInUseException("Cannot delete label: it is used in label links");
        }
        repository.deleteById(id);
    }
}
