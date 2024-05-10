package com.t3t.bookstoreapi.elastic.service;

import com.t3t.bookstoreapi.elastic.repository.ElasticRepository;
import com.t3t.bookstoreapi.elastic.util.Constants;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AutocompleteService {
    private final RestHighLevelClient client;
    private final ElasticRepository elasticRepository;

    public BaseResponse<List<String>> autocomplete(String prefix) throws IOException {
        SearchRequest searchRequest = new SearchRequest("t3t_book");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = elasticRepository.autocomplete(prefix);
        searchSourceBuilder.query(boolQuery);
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return parseSuggestResponse(response);
    }

    private BaseResponse<List<String>> parseSuggestResponse(SearchResponse response) {
        List<String> suggestions = new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            suggestions.add((String) hit.getSourceAsMap().get("book_name"));
        }
        return new BaseResponse<>("book_name",suggestions);
    }
}
