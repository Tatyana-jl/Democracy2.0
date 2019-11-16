package com.junction.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.junction.domain.Problem} entity.
 */
public class ProblemDTO implements Serializable {

    private Long id;

    private Double latitude;

    private Double longitude;

    @Lob
    private byte[] imageBefore;

    private String imageBeforeContentType;
    @Lob
    private byte[] imageAfter;

    private String imageAfterContentType;
    private String category;

    private Integer voteCounter;

    private Instant startTime;

    private Instant endTime;


    private Long problemTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public byte[] getImageBefore() {
        return imageBefore;
    }

    public void setImageBefore(byte[] imageBefore) {
        this.imageBefore = imageBefore;
    }

    public String getImageBeforeContentType() {
        return imageBeforeContentType;
    }

    public void setImageBeforeContentType(String imageBeforeContentType) {
        this.imageBeforeContentType = imageBeforeContentType;
    }

    public byte[] getImageAfter() {
        return imageAfter;
    }

    public void setImageAfter(byte[] imageAfter) {
        this.imageAfter = imageAfter;
    }

    public String getImageAfterContentType() {
        return imageAfterContentType;
    }

    public void setImageAfterContentType(String imageAfterContentType) {
        this.imageAfterContentType = imageAfterContentType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getVoteCounter() {
        return voteCounter;
    }

    public void setVoteCounter(Integer voteCounter) {
        this.voteCounter = voteCounter;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getProblemTypeId() {
        return problemTypeId;
    }

    public void setProblemTypeId(Long problemTypeId) {
        this.problemTypeId = problemTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProblemDTO problemDTO = (ProblemDTO) o;
        if (problemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), problemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProblemDTO{" +
            "id=" + getId() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", imageBefore='" + getImageBefore() + "'" +
            ", imageAfter='" + getImageAfter() + "'" +
            ", category='" + getCategory() + "'" +
            ", voteCounter=" + getVoteCounter() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", problemType=" + getProblemTypeId() +
            "}";
    }
}
