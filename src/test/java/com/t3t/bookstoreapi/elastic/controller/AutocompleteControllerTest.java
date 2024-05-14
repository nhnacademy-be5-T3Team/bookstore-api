package com.t3t.bookstoreapi.elastic.controller;

import com.t3t.bookstoreapi.elastic.service.AutocompleteService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AutocompleteControllerTest {

    @Mock
    private AutocompleteService autocompleteService;

    @InjectMocks
    private AutocompleteController autocompleteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("자동완성 test")
    void autocompleteTest() throws IOException {
        // Given
        String prefix = "test";
        List<String> expectedData = Arrays.asList("example");
        BaseResponse<List<String>> serviceResponse = new BaseResponse<>();
        serviceResponse.setData(expectedData);

        when(autocompleteService.autocomplete(prefix)).thenReturn(serviceResponse);

        // When
        ResponseEntity<BaseResponse<List<String>>> response = autocompleteController.autocomplete(prefix);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedData, response.getBody().getData());
    }
    @Test
    @DisplayName("자동완성 API 테스트 - 예외 처리")
    public void testAutocompleteError() throws IOException {
        // Given
        String prefix = "test";
        when(autocompleteService.autocomplete(prefix)).thenThrow(IOException.class);

        // When
        ResponseEntity<BaseResponse<List<String>>> responseEntity = autocompleteController.autocomplete(prefix);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("잘못된 접근입니다.", responseEntity.getBody().getMessage());
    }
}
