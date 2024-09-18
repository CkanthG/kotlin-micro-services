package com.sree.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * This class holds the user information.
 */
@Document
class User {
    @Id
    var id: Int? = null
    var username: String = ""
    var email: String = ""
    var mobileNumber: String = ""
    var password: String = ""
        get() = field
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }
    var address: Address? = null
}

/**
 * This data class holds the address information.
 */
data class Address(
    val street:String,
    val city:String,
    val country:String,
    val zipcode:Int
)
