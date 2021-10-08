package com.popovich.filestorage.service;

import com.popovich.filestorage.document.File;
import com.popovich.filestorage.dto.RequestUploadDto;
import com.popovich.filestorage.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class FileService {

    private FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public File addFile(RequestUploadDto dto) {
        HashSet<String> tags = new HashSet<>();
        if (dto.getName().contains(".")) {
            String ext = dto.getName().toLowerCase().substring(dto.getName().lastIndexOf(".") + 1);
            tags.add(ext);
        }

        var file = File.builder()
                .name(dto.getName())
                .size(dto.getSize())
                .tags(tags)
                .build();
        return repository.save(file);
    }

    public Boolean deleteFile(String ID) throws NoSuchObjectException {
        var resultOpt = repository.findById(ID);
        if (resultOpt.isEmpty()) {
            throw new NoSuchObjectException("no such object");
        }
        repository.deleteById(ID);
        return true;
    }

    public Boolean assignTags(String ID, List<String> tags) throws NoSuchObjectException {
        log.info("SERVICE IS ASSIGNING TAGS NOW");
        var fileOptional = repository.findById(ID);
        if (fileOptional.isEmpty()) {
            throw new NoSuchObjectException("no such file");
        }
        var file = fileOptional.get();
        Set<String> inputTagSet = new HashSet<>(tags);
        log.info("OLD TAGS: {} || NEW TAGS: {}", file.getTags(), inputTagSet);
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
