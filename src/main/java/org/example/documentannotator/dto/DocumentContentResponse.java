package org.example.documentannotator.dto;

public class DocumentContentResponse {
    private String text;
    private String html;
    private Integer pageCount;

    public DocumentContentResponse() {
    }

    public DocumentContentResponse(String text) {
        this.text = text;
    }

    public DocumentContentResponse(String html, Integer pageCount) {
        this.html = html;
        this.pageCount = pageCount;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
}
