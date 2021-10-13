package com.popovich.filestorage.controller;

import com.popovich.filestorage.dto.RequestUploadDto;
import com.popovich.filestorage.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootControllerTest {

    @Autowired
    FileService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testShouldPutFileAndReturnSingleFileDto() throws Exception {

        var result = service.addFile(new RequestUploadDto("testtest", 125L));
        log.info("file is null: {}", result == null);
        log.info("file if: {}", result.getId());
        mockMvc.perform(get("/file/{ID}", result.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(result.getId()))
                .andReturn();
    }
}
