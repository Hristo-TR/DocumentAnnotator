package org.example.documentannotator.controller;

import jakarta.validation.Valid;
import org.example.documentannotator.converter.AnnotationConverter;
import org.example.documentannotator.converter.DocumentConverter;
import org.example.documentannotator.data.enumeration.FileType;
import org.example.documentannotator.dto.AnnotationDto;
import org.example.documentannotator.dto.CreateTextDocumentRequest;
import org.example.documentannotator.dto.DocumentContentResponse;
import org.example.documentannotator.dto.DocumentDto;
import org.example.documentannotator.service.AnnotationService;
import org.example.documentannotator.service.DocumentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final DocumentConverter documentConverter;
    private final AnnotationService annotationService;
    private final AnnotationConverter annotationConverter;

    public DocumentController(DocumentService documentService,
                              DocumentConverter documentConverter,
                              AnnotationService annotationService,
                              AnnotationConverter annotationConverter) {
        this.documentService = documentService;
        this.documentConverter = documentConverter;
        this.annotationService = annotationService;
        this.annotationConverter = annotationConverter;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentDto> uploadDocument(@RequestParam("file") MultipartFile file) {
        try {
            var document = documentService.uploadDocument(file);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(documentConverter.toDto(document));
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload document", e);
        }
    }

    @PostMapping(value = "/text", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DocumentDto> createTextDocument(@Valid @RequestBody CreateTextDocumentRequest request) {
        try {
            var document = documentService.createTextDocument(request.getName(), request.getText());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(documentConverter.toDto(document));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create text document", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAllDocuments(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) FileType type) {
        var documents = documentService.findAll(q, type);
        return ResponseEntity.ok(documentConverter.toDtoList(documents));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getDocument(@PathVariable Long id) {
        var document = documentService.findById(id);
        return ResponseEntity.ok(documentConverter.toDto(document));
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<DocumentContentResponse> getDocumentContent(@PathVariable Long id) {
        var document = documentService.findById(id);
        String content = documentService.getDocumentContent(id);

        DocumentContentResponse response = new DocumentContentResponse();
        if (document.getFileType() == FileType.TXT) {
            response.setText(content);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/html")
    public ResponseEntity<String> getDocumentAsHtml(@PathVariable Long id) {
        String html = documentService.getDocumentAsHtml(id);
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(html);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        var document = documentService.findById(id);
        byte[] fileContent = documentService.downloadDocument(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", document.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileContent);
    }

    @GetMapping("/{id}/annotations")
    public ResponseEntity<List<AnnotationDto>> getDocumentAnnotations(@PathVariable Long id) {
        var annotations = annotationService.findByDocumentId(id);
        return ResponseEntity.ok(annotationConverter.toDtoList(annotations));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
