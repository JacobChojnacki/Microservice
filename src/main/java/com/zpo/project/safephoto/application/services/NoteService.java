package com.zpo.project.safephoto.application.services;

import com.zpo.project.safephoto.application.dtos.request.AddNoteRequestDto;
import com.zpo.project.safephoto.application.dtos.request.GetNotesRequestDto;
import com.zpo.project.safephoto.application.dtos.response.GetNotesResponseDto;
import com.zpo.project.safephoto.application.interfaces.INoteService;
import org.springframework.stereotype.Service;

@Service
public class NoteService implements INoteService {


    @Override
    public long addNote(AddNoteRequestDto dto, String token) {
        return 0;
    }

    @Override
    public GetNotesResponseDto getNote(long id, String token) {
        return null;
    }

    @Override
    public GetNotesResponseDto[] getNotes(GetNotesRequestDto dto, String token) {
        return new GetNotesResponseDto[0];
    }
}
