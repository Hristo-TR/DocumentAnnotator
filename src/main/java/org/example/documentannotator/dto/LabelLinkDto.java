package org.example.documentannotator.dto;

public class LabelLinkDto {
    private Long id;
    private String description;
    private Long fromLabelId;
    private Long toLabelId;

    public LabelLinkDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFromLabelId() {
        return fromLabelId;
    }

    public void setFromLabelId(Long fromLabelId) {
        this.fromLabelId = fromLabelId;
    }

    public Long getToLabelId() {
        return toLabelId;
    }

    public void setToLabelId(Long toLabelId) {
        this.toLabelId = toLabelId;
    }
}
