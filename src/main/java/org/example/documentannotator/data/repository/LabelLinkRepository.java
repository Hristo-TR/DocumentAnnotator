package org.example.documentannotator.data.repository;

import org.example.documentannotator.data.entity.LabelLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelLinkRepository extends JpaRepository<LabelLink, Long> {
}
