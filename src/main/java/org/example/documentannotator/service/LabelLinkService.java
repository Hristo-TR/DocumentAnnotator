package org.example.documentannotator.service;

import org.example.documentannotator.data.entity.LabelLink;

import java.util.List;

public interface LabelLinkService extends BaseService<LabelLink> {
    List<LabelLink> findByFromLabelId(Long fromLabelId);

    List<LabelLink> findByToLabelId(Long toLabelId);
}
