package com.zpo.project.safephoto.application.dtos.request;

public class AddNoteRequestDto {
    private String noteJsonString;

    public void setNoteJsonString(String noteJsonString) {
        this.noteJsonString = noteJsonString;
    }

    public String getNoteJsonString() {
        return noteJsonString;
    }
}
