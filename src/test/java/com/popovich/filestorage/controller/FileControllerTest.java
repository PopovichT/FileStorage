package com.popovich.filestorage.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.popovich.filestorage.dto.RequestUploadDto;
import com.popovich.filestorage.mother.FileObjectMother;
import com.popovich.filestorage.service.FileSearchService;
import com.popovich.filestorage.service.FileService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.rmi.NoSuchObjectException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class FileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private FileService service;

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
}
