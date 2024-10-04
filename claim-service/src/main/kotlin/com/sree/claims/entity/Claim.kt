package com.sree.claims.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType

/**
 * Holds claim data to interact with DB.
 */
@Entity
@Table(name = "claims")
class Claim(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val claimId: Long?,
    val claimName: String? = null,
    val market: String? = null,
    val subMarket: Int,
    val claimNumber: String? = null,
    val claimTotalRequested: Long,
    val claimTotalPaid: Long,
    val claimTotalInitial: Long,
    val requestedUser: String? = null
)