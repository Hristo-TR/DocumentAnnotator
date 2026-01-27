package org.example.documentannotator.controller;

import org.example.documentannotator.dto.LabelDensityResponse;
import org.example.documentannotator.dto.SummaryResponse;
import org.example.documentannotator.dto.TopLabelResponse;
import org.example.documentannotator.service.ReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/reports")
public class ReportsController {
    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }

    @GetMapping("/top-labels")
    public ResponseEntity<List<TopLabelResponse>> getTopLabels(
            @RequestParam(required = false) Long documentId) {
        List<TopLabelResponse> topLabels = reportsService.getTopLabels(documentId);
        return ResponseEntity.ok(topLabels);
    }

    @GetMapping("/label-density")
    public ResponseEntity<List<LabelDensityResponse>> getLabelDensity(
            @RequestParam Long documentId,
            @RequestParam Long labelId) {
        List<LabelDensityResponse> density = reportsService.getLabelDensity(documentId, labelId);
        return ResponseEntity.ok(density);
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary() {
        SummaryResponse summary = reportsService.getSummary();
        return ResponseEntity.ok(summary);
    }
}
