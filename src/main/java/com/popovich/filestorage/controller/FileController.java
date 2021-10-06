package com.popovich.filestorage.controller;

import com.popovich.filestorage.dto.RequestUploadDto;
import com.popovich.filestorage.dto.ResponseDto;
import com.popovich.filestorage.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FileController {

    private static final HttpStatus status200 = HttpStatus.OK;
    private final FileService service;

    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    @PostMapping("/file")
    public ResponseEntity<ResponseDto> uploadFile(@RequestBody RequestUploadDto dto) {
        var savedFile = service.addFile(dto);
        var responseDto = ResponseDto.builder()
                .ID(savedFile.getId())
                .success(true)
                .build();
        return new ResponseEntity<>(responseDto, status200);
    }

    @DeleteMapping("/file/{ID}")
    public ResponseEntity<ResponseDto> deleteFile(@PathVariable String ID) throws Exception {
        if (service.deleteFile(ID)) {
            var responseDto = ResponseDto.builder().success(true).build();
            return new ResponseEntity<>(responseDto, status200);
        }
        else throw new Exception("service file deleting error");
    }

    @PostMapping("/file/{ID}/tags")
    public ResponseEntity<ResponseDto> assignTagsToFile(@PathVariable String ID, @RequestBody List<String> tags) throws Exception {
        if (service.assignTags(ID, tags)) {
            var responseDto = ResponseDto.builder().success(true).build();
            return new ResponseEntity<>(responseDto, status200);
        }
        else throw new Exception("service tags assigning error");
    }

    @DeleteMapping("/file/{ID}/tags")
    public ResponseEntity<ResponseDto> deleteTagsFromFile(@PathVariable String ID, @RequestBody List<String> tags) throws Exception {
        if (service.deleteTags(ID, tags)) {
            var responseDto = ResponseDto.builder().success(true).build();
            return new ResponseEntity<>(responseDto, status200);
        }
        else throw new Exception("service tag deleting error");
    }
}
