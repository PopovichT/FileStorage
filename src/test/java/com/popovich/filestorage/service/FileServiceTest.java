package com.popovich.filestorage.service;

import com.popovich.filestorage.dto.RequestUploadDto;
import com.popovich.filestorage.mother.FileObjectMother;
import com.popovich.filestorage.repository.FileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.rmi.NoSuchObjectException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FileServiceTest {

    @MockBean
    private FileRepository repository;

    @Autowired
    private FileService service;


    @Test
    void shouldServiceAddFileWithCorrectInputs() {
        var file = FileObjectMother.valid().tags(new HashSet<>()).build();
        var dto = new RequestUploadDto(file.getName(), file.getSize());
        service.addFile(dto);
        verify(repository).save(file);
    }

    @Test
    void shouldServiceDeleteFile() throws NoSuchObjectException {
        when(repository.findById(any())).thenReturn(Optional.of(FileObjectMother.valid().build()));
        service.deleteFile("qwe");
        verify(repository).deleteById(any());
    }

    @Test
    void shouldServiceAddTags() throws NoSuchObjectException {
        var file = FileObjectMother.valid().tags(new HashSet<>(Set.of("qwe", "zxc", "abc"))).build();
        var fileToSave = FileObjectMother.valid().tags(new HashSet<>(Set.of("qwe", "zxc", "abc", "asd"))).build();
        when(repository.findById(any())).thenReturn(Optional.of(file));
        service.assignTags("ID", List.of("asd", "qwe"));
        verify(repository).save(fileToSave);
    }

    @Test
    void shouldServiceDeleteTags() throws NoSuchObjectException {
        var file = FileObjectMother.valid().tags(new HashSet<>(Set.of("qwe", "zxc", "abc", "asd"))).build();
        var fileToSave = FileObjectMother.valid().tags(new HashSet<>(Set.of("zxc", "abc"))).build();
        when(repository.findById(any())).thenReturn(Optional.of(file));
        service.deleteTags("ID", List.of("asd", "qwe"));
        verify(repository).save(fileToSave);
    }

    @Test
    void testtest(){
        boolean bool = true;
        assertTrue(bool);
    }
}
