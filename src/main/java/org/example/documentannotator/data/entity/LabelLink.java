package org.example.documentannotator.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "label_links")
public class LabelLink implements BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_label_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Label fromLabel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_label_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Label toLabel;

    public LabelLink() {
    }

    public LabelLink(String description, Label fromLabel, Label toLabel) {
        this.description = description;
        this.fromLabel = fromLabel;
        this.toLabel = toLabel;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Label getFromLabel() {
        return fromLabel;
    }

    public void setFromLabel(Label fromLabel) {
        this.fromLabel = fromLabel;
    }

    public Label getToLabel() {
        return toLabel;
    }

    public void setToLabel(Label toLabel) {
        this.toLabel = toLabel;
    }

    /**
     * The README requires directed links labelA -> labelB, where from != to.
     */
    @AssertTrue(message = "fromLabel and toLabel must be different")
    public boolean isFromAndToDifferent() {
        if (fromLabel == null || toLabel == null) {
            return true; // handled by @NotNull; avoid duplicate messages
        }
        if (fromLabel == toLabel) {
            return false;
        }
        if (fromLabel.getId() != null && toLabel.getId() != null) {
            return !fromLabel.getId().equals(toLabel.getId());
        }
        return true;
    }
}
