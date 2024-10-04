package com.sree.claims.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * This will handle all kind of exceptions and send formatted error response.
 */
@RestControllerAdvice
class GlobalAdvice {

    /**
     * Handles all kind of Exceptions and send formatted error response.
     * @param exception will throw if exception occurs.
     * @return formatted response entity.
     */
    @ExceptionHandler(Exception::class)
    fun handle(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.message)
    }

    /**
     * Handles all kind of Validation errors and send formatted error response.
     * @param argumentNotValidException will throw if validation error occurs.
     * @return formatted response entity.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(argumentNotValidException: MethodArgumentNotValidException): ResponseEntity<HashMap<String, String>> {
        val map = HashMap<String, String>()
        argumentNotValidException.fieldErrors.forEach {
                fieldError ->
            map[fieldError.field] = fieldError?.defaultMessage ?: ""
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map)
    }

}