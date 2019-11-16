package com.junction.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Problem.
 */
@Entity
@Table(name = "problem")
public class Problem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Lob
    @Column(name = "image_before")
    private byte[] imageBefore;

    @Column(name = "image_before_content_type")
    private String imageBeforeContentType;

    @Lob
    @Column(name = "image_after")
    private byte[] imageAfter;

    @Column(name = "image_after_content_type")
    private String imageAfterContentType;

    @Column(name = "category")
    private String category;

    @Column(name = "vote_counter")
    private Integer voteCounter;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @OneToOne
    @JoinColumn(unique = true)
    private ProblemType problemType;

    @ManyToMany(mappedBy = "problems")
    @JsonIgnore
    private Set<AdminUser> adminUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Problem latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Problem longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public byte[] getImageBefore() {
        return imageBefore;
    }

    public Problem imageBefore(byte[] imageBefore) {
        this.imageBefore = imageBefore;
        return this;
    }

    public void setImageBefore(byte[] imageBefore) {
        this.imageBefore = imageBefore;
    }

    public String getImageBeforeContentType() {
        return imageBeforeContentType;
    }

    public Problem imageBeforeContentType(String imageBeforeContentType) {
        this.imageBeforeContentType = imageBeforeContentType;
        return this;
    }

    public void setImageBeforeContentType(String imageBeforeContentType) {
        this.imageBeforeContentType = imageBeforeContentType;
    }

    public byte[] getImageAfter() {
        return imageAfter;
    }

    public Problem imageAfter(byte[] imageAfter) {
        this.imageAfter = imageAfter;
        return this;
    }

    public void setImageAfter(byte[] imageAfter) {
        this.imageAfter = imageAfter;
    }

    public String getImageAfterContentType() {
        return imageAfterContentType;
    }

    public Problem imageAfterContentType(String imageAfterContentType) {
        this.imageAfterContentType = imageAfterContentType;
        return this;
    }

    public void setImageAfterContentType(String imageAfterContentType) {
        this.imageAfterContentType = imageAfterContentType;
    }

    public String getCategory() {
        return category;
    }

    public Problem category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getVoteCounter() {
        return voteCounter;
    }

    public Problem voteCounter(Integer voteCounter) {
        this.voteCounter = voteCounter;
        return this;
    }

    public void setVoteCounter(Integer voteCounter) {
        this.voteCounter = voteCounter;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Problem startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public Problem endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public ProblemType getProblemType() {
        return problemType;
    }

    public Problem problemType(ProblemType problemType) {
        this.problemType = problemType;
        return this;
    }

    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType;
    }

    public Set<AdminUser> getAdminUsers() {
        return adminUsers;
    }

    public Problem adminUsers(Set<AdminUser> adminUsers) {
        this.adminUsers = adminUsers;
        return this;
    }

    public Problem addAdminUser(AdminUser adminUser) {
        this.adminUsers.add(adminUser);
        adminUser.getProblems().add(this);
        return this;
    }

    public Problem removeAdminUser(AdminUser adminUser) {
        this.adminUsers.remove(adminUser);
        adminUser.getProblems().remove(this);
        return this;
    }

    public void setAdminUsers(Set<AdminUser> adminUsers) {
        this.adminUsers = adminUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Problem)) {
            return false;
        }
        return id != null && id.equals(((Problem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Problem{" +
            "id=" + getId() +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", imageBefore='" + getImageBefore() + "'" +
            ", imageBeforeContentType='" + getImageBeforeContentType() + "'" +
            ", imageAfter='" + getImageAfter() + "'" +
            ", imageAfterContentType='" + getImageAfterContentType() + "'" +
            ", category='" + getCategory() + "'" +
            ", voteCounter=" + getVoteCounter() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
