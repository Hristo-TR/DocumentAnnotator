package org.example.documentannotator.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTextDocumentRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String text;

    public CreateTextDocumentRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
