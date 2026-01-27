package org.example.documentannotator.controller;

import jakarta.validation.Valid;
import org.example.documentannotator.converter.LabelLinkConverter;
import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.data.entity.LabelLink;
import org.example.documentannotator.dto.LabelLinkDto;
import org.example.documentannotator.service.LabelLinkService;
import org.example.documentannotator.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/label-links")
public class LabelLinkController {
    private final LabelLinkService labelLinkService;
    private final LabelService labelService;
    private final LabelLinkConverter labelLinkConverter;

    public LabelLinkController(LabelLinkService labelLinkService,
                               LabelService labelService,
                               LabelLinkConverter labelLinkConverter) {
        this.labelLinkService = labelLinkService;
        this.labelService = labelService;
        this.labelLinkConverter = labelLinkConverter;
    }

    @PostMapping
    public ResponseEntity<LabelLinkDto> createLabelLink(@Valid @RequestBody LabelLinkDto labelLinkDto) {
        LabelLink labelLink = labelLinkConverter.toEntity(labelLinkDto);

        Label fromLabel = labelService.findById(labelLinkDto.getFromLabelId());
        Label toLabel = labelService.findById(labelLinkDto.getToLabelId());

        labelLink.setFromLabel(fromLabel);
        labelLink.setToLabel(toLabel);

        LabelLink saved = labelLinkService.save(labelLink);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(labelLinkConverter.toDto(saved));
    }

    @GetMapping
    public ResponseEntity<List<LabelLinkDto>> getAllLabelLinks(
            @RequestParam(required = false) Long fromLabelId,
            @RequestParam(required = false) Long toLabelId) {
        List<LabelLink> labelLinks;

        if (fromLabelId != null && toLabelId != null) {
            labelLinks = labelLinkService.findByFromLabelId(fromLabelId).stream()
                    .filter(link -> link.getToLabel().getId().equals(toLabelId))
                    .toList();
        } else if (fromLabelId != null) {
            labelLinks = labelLinkService.findByFromLabelId(fromLabelId);
        } else if (toLabelId != null) {
            labelLinks = labelLinkService.findByToLabelId(toLabelId);
        } else {
            labelLinks = labelLinkService.getRepository().findAll();
        }

        return ResponseEntity.ok(labelLinkConverter.toDtoList(labelLinks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabelLinkDto> getLabelLink(@PathVariable Long id) {
        LabelLink labelLink = labelLinkService.findById(id);
        return ResponseEntity.ok(labelLinkConverter.toDto(labelLink));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabelLinkDto> updateLabelLink(@PathVariable Long id, @Valid @RequestBody LabelLinkDto labelLinkDto) {
        LabelLink existing = labelLinkService.findById(id);
        LabelLink updated = labelLinkConverter.toEntity(labelLinkDto);

        updated.setId(id);

        Label fromLabel = labelService.findById(labelLinkDto.getFromLabelId());
        Label toLabel = labelService.findById(labelLinkDto.getToLabelId());

        updated.setFromLabel(fromLabel);
        updated.setToLabel(toLabel);

        LabelLink saved = labelLinkService.save(updated);
        return ResponseEntity.ok(labelLinkConverter.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabelLink(@PathVariable Long id) {
        labelLinkService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
