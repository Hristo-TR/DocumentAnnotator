package org.example.documentannotator.data.enumeration;

public enum FileType {
    TXT(".txt"),
    PDF(".pdf"),
    DOCX(".docx");

    private String value;

    FileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
