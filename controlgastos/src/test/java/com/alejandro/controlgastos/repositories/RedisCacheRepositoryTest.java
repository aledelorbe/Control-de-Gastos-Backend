package com.alejandro.controlgastos.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.alejandro.controlgastos.entities.ExpenseCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
class RedisCacheRepositoryTest {
    
    // To create the mocks
    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ValueOperations<String, String> valueOperations;

    // To create a service object with the injection of a mock
    @InjectMocks
    private RedisCacheRepository redisCacheRepository;


    // To test the get method when the key exists in redis
    @Test
    void shouldReturnObjectWhenKeyExistsInRedis() throws Exception {

        // Given
        String key = "expenseCategories";
        String json = """
            [
                {
                    "id":1,
                    "name":"Food"
                }
            ]
            """;
        List<ExpenseCategory> expectedCategories = List.of(
                new ExpenseCategory()
        );
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(json);
        when(objectMapper.readValue(eq(json), any(TypeReference.class)))
                .thenReturn(expectedCategories);
                
        // Then
        List<ExpenseCategory> result =
                redisCacheRepository.get(
                    key,
                    new TypeReference<List<ExpenseCategory>>() {}
                );

        // Then
        assertEquals(expectedCategories, result);

        verify(valueOperations).get(key);
        verify(objectMapper).readValue(eq(json), any(TypeReference.class));
    }

    // To test the get method when it returns null
    @Test
    void shouldReturnNullWhenKeyDoesNotExistInRedis() {

        // Given
        String key = "expenseCategories";
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(key)).thenReturn(null);

        // When
        List<ExpenseCategory> result =
                redisCacheRepository.get(
                        key,
                        new TypeReference<List<ExpenseCategory>>() {}
                );

        // Then
        assertNull(result);

        verify(valueOperations).get(key);
        verifyNoInteractions(objectMapper);
    }

    // To test the get method when it throwing the JsonProcessingException Exception
    @Test
    void shouldThrowRuntimeExceptionWhenDeserializationFails() throws Exception {

        // Given
        String key = "expenseCategories";
        String json = "invalid-json";

        when(valueOperations.get(key)).thenReturn(json);
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(objectMapper.readValue(eq(json), any(TypeReference.class)))
                .thenThrow(JsonProcessingException.class);

        // When and Then
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> redisCacheRepository.get(
                    key,
                    new TypeReference<List<ExpenseCategory>>() {}
            )
        );

        assertTrue(
            exception.getMessage()
                    .contains("Failed to deserialize value for key")
        );
    }

    // To test the set method when the key is saved sucessfully
    @Test
    void shouldSaveValueInRedisSuccessfully() throws Exception {

        // Given
        String key = "expenseCategories";
        long ttl = 60;
        List<String> categories = List.of("Food", "Transport");
        String json = """
            ["Food", "Transport"]
            """;

        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
        when(objectMapper.writeValueAsString(categories))
                .thenReturn(json);

        // When
        redisCacheRepository.set(key, categories, ttl);

        // Then
        verify(valueOperations).set(
                key,
                json,
                ttl,
                TimeUnit.MINUTES
        );
    }

    // To test the set method when it throwing the JsonProcessingException Exception
    @Test
    void shouldThrowRuntimeExceptionWhenSerializationFails() throws Exception {

        // Given
        String key = "expenseCategories";
        long ttl = 60;

        List<String> categories = List.of("Food");

        when(objectMapper.writeValueAsString(categories))
                .thenThrow(JsonProcessingException.class);

        // When and Then
        RuntimeException exception = assertThrows(
            RuntimeException.class,
            () -> redisCacheRepository.set(
                    key,
                    categories,
                    ttl
            )
        );

        assertTrue(
            exception.getMessage()
                    .contains("Failed to serialize value for key")
        );
    }

}
