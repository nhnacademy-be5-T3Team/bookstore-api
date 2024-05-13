package com.t3t.bookstoreapi.elastic.controller;

import com.t3t.bookstoreapi.elastic.service.AutocompleteService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import org.junit.jupiter.api.BeforeEach;
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
    void autocomplete_ReturnsExpectedResults() throws IOException {
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
}
