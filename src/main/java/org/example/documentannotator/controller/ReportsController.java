package org.example.documentannotator.controller;

import org.example.documentannotator.dto.LabelDensityResponse;
import org.example.documentannotator.dto.SummaryResponse;
import org.example.documentannotator.dto.TopLabelResponse;
import org.example.documentannotator.service.ReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/reports")
public class ReportsController {
    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    /**
     * Retrieves a ranking of the most used labels, including their names and times used
     * optionally show only for specified document
     */
    @GetMapping("/top-labels")
    public ResponseEntity<List<TopLabelResponse>> getTopLabels(
            @RequestParam(required = false) Long documentId) {
        List<TopLabelResponse> topLabels = reportsService.getTopLabels(documentId);
        return ResponseEntity.ok(topLabels);
    }

    /**
     * Retrieves annotation count per page for that document and label
     * Only works for PDF files
     */
    @GetMapping("/label-density")
    public ResponseEntity<List<LabelDensityResponse>> getLabelDensity(
            @RequestParam Long documentId,
            @RequestParam Long labelId) {
        List<LabelDensityResponse> density = reportsService.getLabelDensity(documentId, labelId);
        return ResponseEntity.ok(density);
    }

    /**
     * Retrieves the counts of documents, labels, links and annotations
     */
    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary() {
        SummaryResponse summary = reportsService.getSummary();
        return ResponseEntity.ok(summary);
    }
}
