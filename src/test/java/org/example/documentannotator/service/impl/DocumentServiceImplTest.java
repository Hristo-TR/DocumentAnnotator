package org.example.documentannotator.service.impl;

import org.example.documentannotator.data.entity.Document;
import org.example.documentannotator.data.enumeration.FileType;
import org.example.documentannotator.data.exception.DuplicateDocumentException;
import org.example.documentannotator.data.repository.AnnotationRepository;
import org.example.documentannotator.data.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DocumentServiceImplTest {

    @TempDir
    Path tempDir;
    @Mock
    private DocumentRepository documentRepository;
    @Mock
    private AnnotationRepository annotationRepository;

    private DocumentServiceImpl documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // recreate service with a real temp storage path so file operations work
        documentService = new DocumentServiceImpl(documentRepository, annotationRepository, tempDir.toString());
    }

    @Test
    void uploadDocument_savesFileAndEntity() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "hello world".getBytes()
        );

        when(documentRepository.findByName("test.txt")).thenReturn(Optional.empty());
        when(documentRepository.save(any(Document.class))).thenAnswer(invocation -> {
            Document doc = invocation.getArgument(0);
            doc.setId(1L);
            return doc;
        });

        Document result = documentService.uploadDocument(file);

        assertNotNull(result.getId());
        assertEquals("test.txt", result.getName());
        assertEquals(FileType.TXT, result.getFileType());

        // ensure file actually exists on disk
        assertTrue(Files.exists(Path.of(result.getPath())));

        verify(documentRepository).findByName("test.txt");
        verify(documentRepository).save(any(Document.class));
    }

    @Test
    void uploadDocument_duplicateName_throwsException() {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "duplicate.txt",
                "text/plain",
                "hello".getBytes()
        );

        when(documentRepository.findByName("duplicate.txt"))
                .thenReturn(Optional.of(new Document()));

        assertThrows(DuplicateDocumentException.class, () -> documentService.uploadDocument(file));
        verify(documentRepository, never()).save(any());
    }

    @Test
    void findAll_filtersByQueryAndType() {
        Document txtDoc = new Document();
        txtDoc.setName("notes.txt");
        txtDoc.setFileType(FileType.TXT);

        Document pdfDoc = new Document();
        pdfDoc.setName("notes.pdf");
        pdfDoc.setFileType(FileType.PDF);

        when(documentRepository.findByNameContainingIgnoreCase("notes"))
                .thenReturn(java.util.List.of(txtDoc, pdfDoc));

        var result = documentService.findAll("notes", FileType.PDF);

        assertEquals(1, result.size());
        assertEquals(FileType.PDF, result.getFirst().getFileType());
    }
}

