package org.example.documentannotator.controller;

import org.example.documentannotator.service.AnnotationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/annotation")
public class AnnotationController {
    private final AnnotationService annotationService;

    public AnnotationController(AnnotationService annotationService) {
        this.annotationService = annotationService;
    }
}
