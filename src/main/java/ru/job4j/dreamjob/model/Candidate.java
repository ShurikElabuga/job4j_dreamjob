package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int id;
    private String title;
    private String description;
    private LocalDateTime creationDate = LocalDateTime.now();
    private boolean visible;
    private int cityId;

    public Candidate(int id, String title, String description, LocalDateTime creationDate, boolean visible, int cityId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.visible = visible;
        this.cityId = cityId;
        this.creationDate = creationDate;
    }

    public Candidate() {
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
