package org.example.documentannotator.controller;

import jakarta.validation.Valid;
import org.example.documentannotator.converter.LabelConverter;
import org.example.documentannotator.data.entity.Label;
import org.example.documentannotator.dto.LabelDto;
import org.example.documentannotator.service.LabelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;
    private final LabelConverter labelConverter;

    public LabelController(LabelService labelService, LabelConverter labelConverter) {
        this.labelService = labelService;
        this.labelConverter = labelConverter;
    }

    /**
     * Create a new label
     */
    @PostMapping
    public ResponseEntity<LabelDto> createLabel(@Valid @RequestBody LabelDto labelDto) {
        Label label = labelConverter.toEntity(labelDto);

        if (labelDto.getParentLabelId() != null) {
            Label parent = labelService.findById(labelDto.getParentLabelId());
            label.setParentLabel(parent);
        }

        Label saved = labelService.save(label);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(labelConverter.toDto(saved));
    }

    /**
     * Retrieve all labels
     */
    @GetMapping
    public ResponseEntity<List<LabelDto>> getAllLabels(
            @RequestParam(required = false, defaultValue = "false") boolean parents) {
        List<Label> labels = parents ? labelService.findAllParents() : labelService.findAllFlat();
        return ResponseEntity.ok(labelConverter.toDtoList(labels));
    }

    /**
     * Retrieve label with given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<LabelDto> getLabel(@PathVariable Long id) {
        Label label = labelService.findById(id);
        return ResponseEntity.ok(labelConverter.toDto(label));
    }

    /**
     * Update the label with the given id
     */
    @PutMapping("/{id}")
    public ResponseEntity<LabelDto> updateLabel(@PathVariable Long id, @Valid @RequestBody LabelDto labelDto) {
        Label existing = labelService.findById(id);
        labelService.validateParent(id, labelDto.getParentLabelId());

        Label updated = labelConverter.toEntity(labelDto);

        updated.setId(id);
        updated.setCreatedAt(existing.getCreatedAt());
        updated.setUpdatedAt(existing.getUpdatedAt());

        if (labelDto.getParentLabelId() != null) {
            Label parent = labelService.findById(labelDto.getParentLabelId());
            updated.setParentLabel(parent);
        } else {
            updated.setParentLabel(null);
        }

        Label saved = labelService.save(updated);
        return ResponseEntity.ok(labelConverter.toDto(saved));
    }

    /**
     * Delete the label with the given id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabel(@PathVariable Long id) {
        labelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
