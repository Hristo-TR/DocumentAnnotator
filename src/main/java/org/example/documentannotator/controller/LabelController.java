package org.example.documentannotator.controller;

import org.example.documentannotator.service.LabelLinkService;
import org.example.documentannotator.service.LabelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/label")
public class LabelController {
    private final LabelService labelService;
    private final LabelLinkService labelLinkService;

    public LabelController(LabelService labelService, LabelLinkService labelLinkService) {
        this.labelService = labelService;
        this.labelLinkService = labelLinkService;
    }
}
