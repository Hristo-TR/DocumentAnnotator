package org.example.documentannotator.data.repository;

import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.data.entity.LabelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelLinkRepository extends JpaRepository<LabelLink, Long> {
    List<LabelLink> findByFromLabel(Label fromLabel);

    List<LabelLink> findByFromLabelId(Long fromLabelId);

    List<LabelLink> findByToLabel(Label toLabel);

    List<LabelLink> findByToLabelId(Long toLabelId);

    boolean existsByFromLabelId(Long fromLabelId);

    boolean existsByToLabelId(Long toLabelId);
}
