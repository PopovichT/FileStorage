package com.popovich.filestorage.controller;

import com.popovich.filestorage.document.File;
import com.popovich.filestorage.dto.RequestUploadDto;
import com.popovich.filestorage.dto.ResponseDto;
import com.popovich.filestorage.dto.ResponseFileListDto;
import com.popovich.filestorage.service.FileSearchService;
import com.popovich.filestorage.service.FileService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService service;
    private final FileSearchService searchService;

    @Autowired
    public FileController(FileService service, FileSearchService searchService) {
        this.searchService = searchService;
        this.service = service;
    }

    @PostMapping
    public ResponseDto uploadFile(@RequestBody RequestUploadDto dto) {
        var savedFile = service.addFile(dto);
        var responseDto = ResponseDto.builder()
                .ID(savedFile.getId())
                .success(true)
                .build();
        return responseDto;
    }

    @PostMapping("/ping")
    public String ping(@RequestParam String param) {
        return param;
    }


    @DeleteMapping("/{ID}")
    public ResponseDto deleteFile(@PathVariable String ID) throws Exception {
        if (service.deleteFile(ID)) {
            var responseDto = ResponseDto.builder().success(true).build();
            return responseDto;
        }
        else throw new Exception("service file deleting error");
    }

    @PostMapping("/{ID}/tags")
    public ResponseDto assignTagsToFile(@PathVariable String ID, @RequestBody List<String> tags) throws Exception {
        if (service.assignTags(ID, tags)) {
            var responseDto = ResponseDto.builder().success(true).build();
            return responseDto;
        }
        else throw new Exception("service tag assigning error");
    }

    @DeleteMapping("/{ID}/tags")
    public ResponseDto deleteTagsFromFile(@PathVariable String ID, @RequestBody List<String> tags) throws Exception {
        if (service.deleteTags(ID, tags)) {
            var responseDto = ResponseDto.builder().success(true).build();
            return responseDto;
        }
        else throw new Exception("service tag deleting error");
    }

    @GetMapping
    public ResponseFileListDto getFilteredList(@RequestParam(required = false) List<String> tags, @RequestParam(required = false) Integer page, @RequestParam(required = false) Long size) {
         searchService.getFilteredList(tags, page, size);
         var responseDto = ResponseDto.builder().success(true).build();
         return null;
    }
}
