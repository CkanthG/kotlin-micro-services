package com.sree.user.exception

import org.springframework.http.HttpStatus

/**
 * This class holds the UserNotFoundException information.
 */
class UserNotFoundException(
    val httpStatus: HttpStatus,
    override val message: String?,
    override val cause: Throwable? = null
): RuntimeException()