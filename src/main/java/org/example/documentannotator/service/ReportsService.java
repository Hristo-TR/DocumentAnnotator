package org.example.documentannotator.service;

import org.example.documentannotator.dto.LabelDensityResponse;
import org.example.documentannotator.dto.SummaryResponse;
import org.example.documentannotator.dto.TopLabelResponse;

import java.util.List;

public interface ReportsService {
    List<TopLabelResponse> getTopLabels(Long documentId);

    List<LabelDensityResponse> getLabelDensity(Long documentId, Long labelId);

    SummaryResponse getSummary();
}
