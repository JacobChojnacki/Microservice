package com.zpo.project.safephoto.domain.snapshots;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class MetadataSnapshot {
    private String title;
    private String location;
    private ZonedDateTime date;
    private String description;
    private String format;
    private String author;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getFormat() {
        return format;
    }

    public String getAuthor() {
        return author;
    }

    public MetadataSnapshot(String author, String title, String location, ZonedDateTime date, String description, String format) {
        this.author = author;
        this.title = title;
        this.location = location;
        this.date = date;
        this.description = description;
        this.format = format;
    }
}
