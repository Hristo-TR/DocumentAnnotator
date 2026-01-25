package org.example.documentannotator.dto;

public class TopLabelResponse {
    private Long labelId;
    private String labelName;
    private Long count;

    public TopLabelResponse() {
    }

    public TopLabelResponse(Long labelId, String labelName, Long count) {
        this.labelId = labelId;
        this.labelName = labelName;
        this.count = count;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
