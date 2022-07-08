package com.zpo.project.safephoto.infrastructure.models;

import java.time.ZonedDateTime;

public class FilterModel {
    private String title;
    private String location;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private String description;
    private String author;

    public FilterModel(String title, String location, ZonedDateTime startDate, ZonedDateTime endDate, String description, String author) {
        this.title = title;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
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
