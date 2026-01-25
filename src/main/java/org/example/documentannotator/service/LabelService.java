package org.example.documentannotator.service;

import org.example.documentannotator.data.entity.Label;

import java.util.List;

public interface LabelService extends BaseService<Label> {
    List<Label> findAllAsTree();

    List<Label> findAllFlat();

    void validateParent(Long labelId, Long parentLabelId);
}
