package com.bitzen.appmusica.configs;

import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ExceptionHandlerTest {

    @InjectMocks
    private ExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenHandlerBadRequestExceptionThenReturnAResponseEntity() {
        BadRequestException badRequestException = new BadRequestException("Bad Request");

        ResponseEntity<String> response = exceptionHandler.handlerBadRequestException(badRequestException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody());
    }

    @Test
    void whenHandlerNotFoundExceptionThenReturnAResponseEntity() {
        NotFoundException notFoundException = new NotFoundException("Not Found");

        ResponseEntity<String> response = exceptionHandler.handlerNotFoundException(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not Found", response.getBody());
    }

    @Test
    void whenHandlerInternalServerErrorExceptionThenReturnAResponseEntity() {
        Exception exception = new Exception("Unidentified Internal Server Error");

        ResponseEntity<String> response = exceptionHandler.handlerInternalServerErrorException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unidentified Internal Server Error", response.getBody());
    }
}