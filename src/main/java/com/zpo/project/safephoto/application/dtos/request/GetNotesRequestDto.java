package com.zpo.project.safephoto.application.dtos.request;

public class GetNotesRequestDto {
    private String searchFor;

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public String getSearchFor() {
        return searchFor;
    }
}
