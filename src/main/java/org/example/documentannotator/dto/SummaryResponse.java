package org.example.documentannotator.dto;

public class SummaryResponse {
    private Long documentsCount;
    private Long labelsCount;
    private Long labelLinksCount;
    private Long annotationsCount;

    public SummaryResponse() {
    }

    public SummaryResponse(Long documentsCount, Long labelsCount, Long labelLinksCount, Long annotationsCount) {
        this.documentsCount = documentsCount;
        this.labelsCount = labelsCount;
        this.labelLinksCount = labelLinksCount;
        this.annotationsCount = annotationsCount;
    }

    public Long getDocumentsCount() {
        return documentsCount;
    }

    public void setDocumentsCount(Long documentsCount) {
        this.documentsCount = documentsCount;
    }

    public Long getLabelsCount() {
        return labelsCount;
    }

    public void setLabelsCount(Long labelsCount) {
        this.labelsCount = labelsCount;
    }

    public Long getLabelLinksCount() {
        return labelLinksCount;
    }

    public void setLabelLinksCount(Long labelLinksCount) {
        this.labelLinksCount = labelLinksCount;
    }

    public Long getAnnotationsCount() {
        return annotationsCount;
    }

    public void setAnnotationsCount(Long annotationsCount) {
        this.annotationsCount = annotationsCount;
    }
}
