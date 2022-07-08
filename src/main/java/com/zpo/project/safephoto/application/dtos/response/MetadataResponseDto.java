package com.zpo.project.safephoto.application.dtos.response;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MetadataResponseDto {
    private String title;
    private String location;
    private String date;
    private String description;
    private String format;
    private String author;

    public MetadataResponseDto(String author, String title, String location, ZonedDateTime date, String description, String format) {
        this.author = author;
        this.title = title;
        this.location = location;
        this.date = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.description = description;
        this.format = format;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
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






}
