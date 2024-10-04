package com.sree.user.advice

import com.sree.user.exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * This class is used to handle all exceptions.
 */
@RestControllerAdvice
class CustomRestControllerAdvice {

    /**
     * This method is used to handle UserNotFoundException exception.
     */
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(userNotFoundException: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(userNotFoundException.httpStatus).body(userNotFoundException.message)
    }

    /**
     * This method is used to handle MethodArgumentNotValidException exception.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        methodArgumentNotValidException: MethodArgumentNotValidException
    ): ResponseEntity<HashMap<String, String>> {
        val map = HashMap<String, String>()
        methodArgumentNotValidException.fieldErrors.forEach {
                fieldError ->
            map[fieldError.field] = fieldError?.defaultMessage ?: ""
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map)
    }

    /**
     * This method is used to handle all Exceptions.
     */
    @ExceptionHandler(Exception::class)
    fun handleException(exception: java.lang.Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.message)
    }

}