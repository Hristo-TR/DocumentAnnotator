package org.example.documentannotator.dto;

import java.math.BigDecimal;

public class LabelDensityResponse {
    private String bucket;
    private Long count;
    private BigDecimal startRange;
    private BigDecimal endRange;

    public LabelDensityResponse() {
    }

    public LabelDensityResponse(String bucket, Long count) {
        this.bucket = bucket;
        this.count = count;
    }

    public LabelDensityResponse(String bucket, Long count, BigDecimal startRange, BigDecimal endRange) {
        this.bucket = bucket;
        this.count = count;
        this.startRange = startRange;
        this.endRange = endRange;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public BigDecimal getStartRange() {
        return startRange;
    }

    public void setStartRange(BigDecimal startRange) {
        this.startRange = startRange;
    }

    public BigDecimal getEndRange() {
        return endRange;
    }

    public void setEndRange(BigDecimal endRange) {
        this.endRange = endRange;
    }
}
