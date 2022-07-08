package com.zpo.project.safephoto.application.dtos.request;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class GetPhotosRequestDto {
    private String title;
    private String location;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String description;
    private String author;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }
}
