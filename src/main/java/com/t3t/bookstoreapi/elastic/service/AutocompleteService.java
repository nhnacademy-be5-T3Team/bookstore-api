package com.t3t.bookstoreapi.elastic.service;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutocompleteService {

    private final RestHighLevelClient client;
    public BaseResponse<List<String>> autocomplete(String prefix) throws IOException {

        SearchRequest searchRequest = new SearchRequest("t3t_book"); // Elasticsearch 인덱스 이름
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        CompletionSuggestionBuilder completionSuggestionBuilder =
                SuggestBuilders.completionSuggestion("book_name.book_name2") // 자동완성 필드 설정
                        .prefix(prefix)
                        .size(10);

        searchSourceBuilder.suggest(new SuggestBuilder().addSuggestion("suggest_book_name", completionSuggestionBuilder));
        searchRequest.source(searchSourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        return parseSuggestResponse(response, "suggest_book_name");

    }


    private BaseResponse<List<String>> parseSuggestResponse(SearchResponse response, String suggestionName) {
        List<String> suggestions = response.getSuggest().getSuggestion(suggestionName).getEntries().stream()
                .flatMap(entry -> entry.getOptions().stream())
                .map(option -> option.getText().string())
                .collect(Collectors.toList());

        BaseResponse<List<String>> baseResponse = new BaseResponse<>();
        baseResponse.setData(suggestions);
        return baseResponse;
    }
}
