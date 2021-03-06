package com.popovich.filestorage.repository;

import com.popovich.filestorage.document.File;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface FileRepository extends ElasticsearchRepository<File, String> {
    List<File> findByNameContainingIgnoreCase(String name);
}
