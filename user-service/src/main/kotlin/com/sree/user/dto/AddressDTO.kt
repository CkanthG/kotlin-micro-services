package com.sree.user.dto

import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated

/**
 * This data class holds the address information.
 */
@Validated
data class AddressDTO(
    @NotBlank(message = "street is mandatory")
    val street:String,
    @NotBlank(message = "city is mandatory")
    val city:String,
    @NotBlank(message = "country is mandatory")
    val country:String,
    @NotBlank(message = "zipcode is mandatory")
    val zipcode:Int
)