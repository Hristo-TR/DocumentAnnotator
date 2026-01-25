package org.example.documentannotator.dto;

public class LabelDto {
    private Long id;
    private String name;
    private String color;
    private Long parentLabelId;
    private Long createdAt;
    private Long updatedAt;

    public LabelDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getParentLabelId() {
        return parentLabelId;
    }

    public void setParentLabelId(Long parentLabelId) {
        this.parentLabelId = parentLabelId;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
