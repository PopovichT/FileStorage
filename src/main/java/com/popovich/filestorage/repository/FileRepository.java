package com.popovich.filestorage.repository;

import com.popovich.filestorage.document.File;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FileRepository extends ElasticsearchRepository<File, String> {
}
