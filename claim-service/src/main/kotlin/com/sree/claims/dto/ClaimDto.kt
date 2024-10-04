package com.sree.claims.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

/**
 * Holds claim details to interact with UI.
 */
data class ClaimDto(
    val claimId: Long? = null,
    @field:NotBlank(message = "claim name is mandatory")
    val claimName: String? = null,
    @field:NotBlank(message = "claim market is mandatory")
    val market: String? = null,
    @field:Min(value = 0, message = "claim sub market should be positive")
    val subMarket: Int,
    @field:NotBlank(message = "claim number is mandatory")
    val claimNumber: String? = null,
    @field:Min(value = 0, message = "claim total requested should be positive")
    val claimTotalRequested: Long,
    @field:Min(value = 0, message = "claim total paid should be positive")
    val claimTotalPaid: Long,
    @field:Min(value = 0, message = "claim total initial should be positive")
    val claimTotalInitial: Long,
    @field:NotBlank(message = "requested user is mandatory")
    var requestedUser: String? = null,
)