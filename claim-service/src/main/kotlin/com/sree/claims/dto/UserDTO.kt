package com.sree.claims.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

/**
 * This data class holds the user information.
 */
data class UserDTO(
    var id:Int? = null,
    @field:NotBlank(message = "username is mandatory")
    var username:String,
    @field:Email(message = "valid email is required")
    var email:String,
    @field:NotBlank(message = "mobile number is mandatory")
    var mobileNumber:String,
    @field:NotBlank(message = "password is mandatory")
    var password:String,
    var address: AddressDTO? = null
)