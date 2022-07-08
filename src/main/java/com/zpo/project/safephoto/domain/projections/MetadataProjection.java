package com.zpo.project.safephoto.domain.projections;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Projekcja to widok, sposób reprezentacji danych domenowych na zewnątrz
 * Może łączyć kilka encji w jedną, dodawać pola jak count itd.
 */
public class MetadataProjection {
    private String title;
    private String location;
    private ZonedDateTime date;
    private String description;
    private String format;

    private String author;

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

    public MetadataProjection(String author, String title, String location, ZonedDateTime date, String description, String format) {
        this.author = author;
        this.title = title;
        this.location = location;
        this.date = date;
        this.description = description;
        this.format = format;

    }
}
