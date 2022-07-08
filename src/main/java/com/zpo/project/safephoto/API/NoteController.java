package com.zpo.project.safephoto.API;

import com.zpo.project.safephoto.application.dtos.request.AddNoteRequestDto;
import com.zpo.project.safephoto.application.dtos.request.GetNotesRequestDto;
import com.zpo.project.safephoto.application.dtos.response.GetNotesResponseDto;
import com.zpo.project.safephoto.application.interfaces.INoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class NoteController {
    private INoteService service;

    @Autowired
    public NoteController(INoteService service) {
        this.service = service;
    }

    @GetMapping("/note/{id}")
    public GetNotesResponseDto getNote(
            @PathVariable(value = "id") long id,
            @RequestHeader("CustomerToken") String token
    ) {
        return service.getNote(id, token);
    }

    @GetMapping("/note")
    public GetNotesResponseDto[] getNotes(
            @RequestParam(value = "searchFor", required = false) String searchFor,
            @RequestHeader("CustomerToken") String token
    ){
        var dto = new GetNotesRequestDto();
        dto.setSearchFor(searchFor);
        return service.getNotes(dto, token);
    }

    @PostMapping("/note")
    public Long postNote(
            @RequestBody AddNoteRequestDto dto,
            @RequestHeader("CustomerToken") String token
    ){
        return service.addNote(dto, token);
    }
}
