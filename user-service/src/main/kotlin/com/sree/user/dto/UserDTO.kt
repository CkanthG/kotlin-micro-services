package com.sree.user.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

/**
 * This data class holds the user information.
 */
data class UserDTO(
    var id:Int? = null,
    @NotBlank(message = "username is mandatory")
    var username:String,
    @NotBlank(message = "email is mandatory")
    @Email(message = "valid email is required")
    var email:String,
    @NotBlank(message = "mobile number is mandatory")
    var mobileNumber:String,
    @NotBlank(message = "password is mandatory")
    var password:String,
    @NotBlank
    var address: AddressDTO? = null
)