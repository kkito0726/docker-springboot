package com.example.simplespringrestapi.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = { NotFoundException.class, BadRequestException.class })
  protected ResponseEntity<Object> handleNotFoundException(HttpException ex, WebRequest request) {
    ErrorResponse errorResponse = new ErrorResponse((ex.getMessage()), ex.getHttpStatus());
    return this.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), ex.getHttpStatus(), request);
  }
}
