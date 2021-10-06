package com.popovich.filestorage.service;

import com.popovich.filestorage.document.File;
import com.popovich.filestorage.dto.RequestUploadDto;
import com.popovich.filestorage.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FileService {

    private FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public File addFile(RequestUploadDto dto) {
        var file = File.builder()
                .name(dto.getName())
                .size(dto.getSize())
                .build();
        return repository.save(file);
    }

    public Boolean deleteFile(String ID) {
        repository.deleteById(ID);
        return true;
    }

    public Boolean assignTags(String ID, List<String> tags) throws NoSuchObjectException {
        var fileOptional = repository.findById(ID);
        if (fileOptional.isEmpty()) {
            throw new NoSuchObjectException("no such file");
        }
        var file = fileOptional.get();
        Set<String> inputTagSet = new HashSet<>(tags);
        Set<String> result = mergeSet(file.getTags(), inputTagSet);
        file.setTags(result);
        repository.save(file);
        return true;
    }

    public Boolean deleteTags(String ID, List<String> tags) throws NoSuchObjectException {
        var fileOptional = repository.findById(ID);
        if (fileOptional.isEmpty()) {
            throw new NoSuchObjectException("no such file");
        }
        var file = fileOptional.get();
        Set<String> inputTagSet = new HashSet<>(tags);
        var savedTags = file.getTags();
        var response = savedTags.removeAll(inputTagSet);
        file.setTags(savedTags);
        repository.save(file);
        return response;
    }

    public static <T> Set<T> mergeSet(Set<T> a, Set<T> b) {
        return new HashSet<T>() {
            {
                addAll(a);
                addAll(b);
            }
        };
    }
}
