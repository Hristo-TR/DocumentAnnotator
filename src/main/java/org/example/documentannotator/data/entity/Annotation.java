package org.example.documentannotator.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.example.documentannotator.data.enumeration.AnnotationType;

import java.math.BigDecimal;

@Entity
@Table(name = "annotations")
public class Annotation implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    @NotNull
    private Document document;

    @ManyToOne
    @JoinColumn(name = "label_id", referencedColumnName = "id")
    @NotNull
    private Label label;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AnnotationType annotationType;

    // TXT fields
    @Column(name = "start_offset")
    @PositiveOrZero
    private Long startOffset;

    @Column(name = "end_offset")
    @PositiveOrZero
    private Long endOffset;

    // PDF, DOCX fields
    @Column(name = "page_number")
    @Positive
    private Integer pageNumber;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal x;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.00")
    private BigDecimal y;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.01")
    private BigDecimal width;

    @Column(precision = 10, scale = 2)
    @DecimalMin(value = "0.01")
    private BigDecimal height;

    public Annotation() {
    }

    public Annotation(Document document, Label label, AnnotationType annotationType, Long startOffset, Long endOffset, Integer pageNumber, BigDecimal x, BigDecimal y, BigDecimal width, BigDecimal height) {
        this.document = document;
        this.label = label;
        this.annotationType = annotationType;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.pageNumber = pageNumber;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public AnnotationType getAnnotationType() {
        return annotationType;
    }

    public void setAnnotationType(AnnotationType annotationType) {
        this.annotationType = annotationType;
    }

    public Long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(Long startOffset) {
        this.startOffset = startOffset;
    }

    public Long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(Long endOffset) {
        this.endOffset = endOffset;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    /**
     * Essential type-specific validation:
     * - TEXT requires startOffset/endOffset and start < end
     * - REGION requires pageNumber and x/y/width/height
     */
    @AssertTrue(message = "Invalid annotation fields for annotationType")
    public boolean isValidForType() {
        if (annotationType == null) {
            return true; // handled by @NotNull; avoid duplicate messages
        }
        return switch (annotationType) {
            case TEXT -> startOffset != null
                    && endOffset != null
                    && startOffset < endOffset;
            case REGION -> pageNumber != null
                    && x != null
                    && y != null
                    && width != null
                    && height != null;
        };
    }
}
