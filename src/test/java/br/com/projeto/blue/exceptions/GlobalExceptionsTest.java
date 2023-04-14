package br.com.projeto.blue.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GlobalExceptionsTest {

    @InjectMocks
    private GlobalExceptions globalExceptions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenEntityNotFoundExceptionThenReturnResponseEntity() {
        ResponseEntity<StandardError> response = globalExceptions
                .entityNotFoundException(new EntityNotFoundException("Nenhum contato encontrada com este ID"));

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Nenhum contato encontrada com este ID", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

    @Test
    void dataIntegratyViolationException() {
        ResponseEntity<StandardError> response = globalExceptions
                .dataIntegratyViolationException(new DataIntegrityViolationException("Email já cadastrado na base de dados!"));

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Email já cadastrado na base de dados!", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

}