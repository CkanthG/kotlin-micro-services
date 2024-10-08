package com.sree.user.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

/**
 * This data class holds the user information.
 */
data class UserDTO(
    var id:Int? = null,
    @field:NotBlank(message = "username is mandatory")
    var username:String? = null,
    @field:Email(message = "valid email is required")
    var email:String? = null,
    @field:NotBlank(message = "mobile number is mandatory")
    var mobileNumber:String? = null,
    @field:NotBlank(message = "password is mandatory")
    var password:String? = null,
    @field:Valid
    var address: AddressDTO? = null
)