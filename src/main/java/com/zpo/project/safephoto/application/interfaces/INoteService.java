package com.zpo.project.safephoto.application.interfaces;

import com.zpo.project.safephoto.application.dtos.request.AddNoteRequestDto;
import com.zpo.project.safephoto.application.dtos.request.GetNotesRequestDto;
import com.zpo.project.safephoto.application.dtos.response.GetNotesResponseDto;
import com.zpo.project.safephoto.domain.Customer;

public interface INoteService {
    long addNote(AddNoteRequestDto dto, String token);
    GetNotesResponseDto getNote(long id, String token);
    GetNotesResponseDto[] getNotes(GetNotesRequestDto dto, String token);
}
