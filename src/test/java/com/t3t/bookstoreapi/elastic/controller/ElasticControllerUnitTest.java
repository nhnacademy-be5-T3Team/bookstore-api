package com.t3t.bookstoreapi.elastic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import com.t3t.bookstoreapi.elastic.service.ElasticService;
import com.t3t.bookstoreapi.model.response.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ElasticController.class)
class ElasticControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElasticService elasticService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getSearchPage_ReturnsResults() throws Exception {
        ElasticResponse elasticResponse = new ElasticResponse();
        elasticResponse.setName("테스트 책");
        elasticResponse.setBookId(new BigDecimal("123"));

        List<ElasticResponse> content = Collections.singletonList(elasticResponse);
        PageResponse<ElasticResponse> pageResponse = new PageResponse<>();
        pageResponse.setContent(content);
        pageResponse.setPageNo(0);
        pageResponse.setPageSize(10);
        pageResponse.setTotalElements(1);
        pageResponse.setTotalPages(1);
        pageResponse.setLast(true);

        when(elasticService.search(anyString(), anyString(), any(Pageable.class))).thenReturn(pageResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                        .param("query", "책")
                        .param("searchType", "book_name")
                        .param("pageNo", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "_score")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].name").value("테스트 책"));
    }


@Test
void getSearchPage_NoResults() throws Exception {
    PageResponse<ElasticResponse> pageResponse = new PageResponse<>();
    pageResponse.setContent(Collections.emptyList());
    pageResponse.setPageNo(0);
    pageResponse.setPageSize(10);
    pageResponse.setTotalElements(0);
    pageResponse.setTotalPages(0);
    pageResponse.setLast(true);

    when(elasticService.search(anyString(), anyString(), any(Pageable.class))).thenReturn(pageResponse);

    mockMvc.perform(MockMvcRequestBuilders.get("/search")
                    .param("query", "없는책")
                    .param("searchType", "book_name")
                    .param("pageNo", "0")
                    .param("pageSize", "10")
                    .param("sortBy", "_score")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
}
    @Test
    void getSearchPage_BadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/search")
                        .param("query", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("잘못된 검색입니다."));
    }
}