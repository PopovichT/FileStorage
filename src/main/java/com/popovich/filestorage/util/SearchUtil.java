package com.popovich.filestorage.util;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public final class SearchUtil {

    private SearchUtil() {
    }

    public static NativeSearchQueryBuilder getQueryBuilder(List<String> tags) {
        var query = new NativeSearchQueryBuilder();

        if ((tags == null) || (tags.isEmpty())) {
            query.withQuery(matchQuery("tags", ""));
            return query;

        } else {
            for (String word : tags) {
                query.withQuery(matchQuery("tags", word).operator(Operator.AND));
            }
        }
        return query;
    }
}
