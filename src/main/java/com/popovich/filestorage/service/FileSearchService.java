package com.popovich.filestorage.service;

import com.popovich.filestorage.dto.ResponseFileListDto;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileSearchService {

    private final RestHighLevelClient highLevelClient;
    private final RestClient client;

    @Autowired
    public FileSearchService(RestHighLevelClient highLevelClient){
        this.highLevelClient = highLevelClient;
        this.client = highLevelClient.getLowLevelClient();
    }

    public ResponseFileListDto getFilteredList(List<String> tags, Integer page, Long size) {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.termsQuery("tags", tags));
        IndexRequest request = new IndexRequest("fileindex");
//        highLevelClient.
//        var searsh = prepareSearch("mongoindex")
//                .setSearchType(SearchType.QUERY_AND_FETCH)
//                .setQuery(boolQuery)
//                .setFrom(0).setSize(60).setExplain(true)
//                .execute()
//                .actionGet();
        return null;
    }
}