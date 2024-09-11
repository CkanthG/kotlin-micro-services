package com.sree.claims.dto

data class ClaimDto(
    val claimId: Long? = null,
    val claimName: String? = null,
    val market: String? = null,
    val subMarket: Int? = null,
    val claimNumber: String? = null,
    val claimTotalRequested: Long? = null,
    val claimTotalPaid: Long? = null,
    val claimTotalInitial: Long? = null,
)