package com.popovich.filestorage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.popovich.filestorage.dto.RequestUploadDto;
import com.popovich.filestorage.dto.SingleFileDto;
import com.popovich.filestorage.mother.FileObjectMother;
import com.popovich.filestorage.service.FileSearchService;
import com.popovich.filestorage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.rmi.NoSuchObjectException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Slf4j
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FileService service;

    @Autowired
    private FileService noMockService;

    @MockBean
    private FileSearchService searchService;

    @Test
    void testValidInputProcessedCorrectly() throws Exception {
        when(service.addFile(any())).thenReturn(FileObjectMother.valid().id("ID").build());

        mockMvc.perform(post("/file")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(new RequestUploadDto("testtest", 125L))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andReturn();
    }

    @Test
    void testInvalidDeleteProcessedCorrectrly() throws Exception {
        when(service.deleteFile(any())).thenThrow(new NoSuchObjectException("test message"));

        mockMvc.perform(delete("/file/12InvalidId21"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("NoSuchObjectException: test message"))
                .andReturn();
    }

    @Test
    void testShouldPutFileAndReturnSingleFileDto() throws Exception {

        var result = noMockService.addFile(new RequestUploadDto("testtest", 125L));
        log.info("file is null: {}", result == null);
        log.info("file if: {}", result.getId());
        mockMvc.perform(get("/file/{ID}", result.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.ID").value(result.getId()))
                .andReturn();
    }
}
