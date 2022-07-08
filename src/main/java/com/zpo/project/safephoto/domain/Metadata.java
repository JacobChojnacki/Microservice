package com.zpo.project.safephoto.domain;

import com.zpo.project.safephoto.domain.snapshots.MetadataSnapshot;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Metadata {
    private String author;
    private String title;
    private String location;
    private ZonedDateTime date;
    private String description;
    private String format;

    public Metadata(String author, String title, String location, ZonedDateTime date, String description, String format) {
        this.author = author;
        this.title = title;
        this.location = location;
        this.date = date;
        this.description = description;
        this.format = format;
    }

    public static Metadata create(String author ,String title, String location, ZonedDateTime date, String description, String format){
        // walidacja
        return new Metadata(author, title, location, date, description, format);
    }

    public String getAuthor() {
        return author;
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


    public static Metadata restoreFromSnapshot(MetadataSnapshot snapshot){
        return new Metadata(snapshot.getAuthor() ,snapshot.getTitle(), snapshot.getLocation(), snapshot.getDate(), snapshot.getDescription(), snapshot.getFormat());
    }


}
