package com.popovich.filestorage.util;

import lombok.NonNull;
import org.elasticsearch.index.query.Operator;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

public final class SearchUtil {

    private SearchUtil() {
    }

    public static NativeSearchQueryBuilder getQueryBuilder(@NonNull List<String> tags) {
        var query = new NativeSearchQueryBuilder();

        for (String word : tags) {
            query.withQuery(matchQuery("tags", word).operator(Operator.AND));
        }

        return query;
    }
}
