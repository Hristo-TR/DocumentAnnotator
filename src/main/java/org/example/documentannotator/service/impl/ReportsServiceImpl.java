package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Annotation;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.example.documentannotator.data.repository.DocumentRepository;
import org.example.documentannotator.data.repository.LabelLinkRepository;
import org.example.documentannotator.data.repository.LabelRepository;
import org.example.documentannotator.dto.LabelDensityResponse;
import org.example.documentannotator.dto.SummaryResponse;
import org.example.documentannotator.dto.TopLabelResponse;
import org.example.documentannotator.service.ReportsService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportsServiceImpl implements ReportsService {

    private final AnnotationRepository annotationRepository;
    private final DocumentRepository documentRepository;
    private final LabelRepository labelRepository;
    private final LabelLinkRepository labelLinkRepository;

    public ReportsServiceImpl(AnnotationRepository annotationRepository,
                              DocumentRepository documentRepository,
                              LabelRepository labelRepository,
                              LabelLinkRepository labelLinkRepository) {
        this.annotationRepository = annotationRepository;
        this.documentRepository = documentRepository;
        this.labelRepository = labelRepository;
        this.labelLinkRepository = labelLinkRepository;
    }

    @Override
    public List<TopLabelResponse> getTopLabels(Long documentId) {
        List<Object[]> results = documentId != null
                ? annotationRepository.findTopLabelsByDocumentId(documentId)
                : annotationRepository.findTopLabelsGlobal();

        return results.stream()
                .map(row -> new TopLabelResponse(
                        (Long) row[0],
                        (String) row[1],
                        (Long) row[2]
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<LabelDensityResponse> getLabelDensity(Long documentId, Long labelId) {
        List<Annotation> annotations =
                annotationRepository.findByDocumentIdAndLabelId(documentId, labelId);

        return annotations.stream()
                .filter(a -> a.getPageNumber() != null)
                .collect(Collectors.groupingBy(
                        Annotation::getPageNumber,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new LabelDensityResponse(
                        "page-" + entry.getKey(),
                        entry.getValue()
                ))
                .sorted(Comparator.comparing(LabelDensityResponse::getBucket))
                .collect(Collectors.toList());
    }

    @Override
    public SummaryResponse getSummary() {
        return new SummaryResponse(
                documentRepository.count(),
                labelRepository.count(),
                labelLinkRepository.count(),
                annotationRepository.count()
        );
    }
}
