package com.sree.claims.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.springframework.validation.annotation.Validated

/**
 * This data class holds the address information.
 */
@Validated
data class AddressDTO(
    @field:NotBlank(message = "street is mandatory")
    val street:String,
    @field:NotBlank(message = "city is mandatory")
    val city:String,
    @field:NotBlank(message = "country is mandatory")
    val country:String,
    @field:Min(value = 12099, message = "zipcode is mandatory")
    val zipcode:Int
)