package com.popovich.filestorage.util;

import lombok.NonNull;
import org.elasticsearch.index.query.Operator;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

public final class SearchUtil {

    private SearchUtil() {
    }

    public static NativeSearchQueryBuilder getQueryBuilder(@NonNull List<String> tags) {
        var query = new NativeSearchQueryBuilder();

        for (String word : tags) {
            query.withQuery(termsQuery("tags", word));
//            query.withQuery(matchQuery("tags", word).operator(Operator.AND)); <---It's much more yummi for tag evaluation
        }

        return query;
    }
}
