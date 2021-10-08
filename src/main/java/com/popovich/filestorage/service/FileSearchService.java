package com.popovich.filestorage.service;

import com.popovich.filestorage.document.File;
import com.popovich.filestorage.dto.ResponseFileListDto;
import com.popovich.filestorage.repository.FileRepository;
import com.popovich.filestorage.util.SearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

@Service
public class FileSearchService {

    private final ElasticsearchRestTemplate template;
    private final FileRepository repository;

    @Autowired
    public FileSearchService(ElasticsearchRestTemplate template, FileRepository repository) {
        this.template = template;
        this.repository = repository;
    }

    public ResponseFileListDto getFilteredList(List<String> tags, Integer page, Integer size) {

        if ((tags == null) || (tags.isEmpty())) {
            Pageable pagination = PageRequest.of(page, size);
            var allResult = repository.findAll(pagination);
            var allCount = repository.count();
            return ResponseFileListDto.builder()
                    .page(allResult.getContent())
                    .total((int) allCount)
                    .build();
        }

        var query = SearchUtil.getQueryBuilder(tags);
//        query.withQuery(matchQuery("name", tags));
        var count = template.count(query.build(), File.class, IndexCoordinates.of("file"));
        var hits = template.search(query.withPageable(PageRequest.of(page, size)).build(), File.class, IndexCoordinates.of("file"));
        var addresses = hits.stream().map(SearchHit::getContent).collect(Collectors.toList());

        return ResponseFileListDto.builder()
                .page(addresses)
                .total((int) count)
                .build();
    }

    public ResponseFileListDto getList(String q) {
        var result = repository.findByNameContainingIgnoreCase(q);
        return ResponseFileListDto.builder()
                .page(result)
                .total(123)
                .build();
    }
}